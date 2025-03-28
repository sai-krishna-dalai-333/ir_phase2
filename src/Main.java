import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // Load stop words
        Set<String> stopWords = loadStopWords("./input/stopwordlist.txt");

        // Initialize Porter stemmer
        Porter stemmer = new Porter();

        // Initialize content parser
        ContentParser parser = new ContentParser(stopWords, stemmer);

        // Initialize indices
        DocumentIndex documentIndex = new DocumentIndex();
        TermIndex termIndex = new TermIndex();

        // Read documents from the 'input' folder and build indices
        File folder = new File("./input");
        if (!folder.exists() || !folder.isDirectory()) {
            System.err.println("Folder ./input/documents does not exist or is not a directory.");
            return;
        }

        int docID = 1;
        for (File file : folder.listFiles()) {
            if (file.isFile()) {
                System.out.println("Processing file: " + file.getAbsolutePath());
                String content = new String(Files.readAllBytes(file.toPath()));
                List<String> words = parser.parse(parseTextContent(content));
                documentIndex.addDoc(docID, words);
                termIndex.addDoc(docID, words);
                docID++;
            }
        }

        // Save indices to files
        documentIndex.writeToFile("./output/forward_index.txt");
        termIndex.writeToFile("./output/inverted_index.txt");

        // Initialize searcher
        Searcher searcher = new Searcher(termIndex);

        // Example search
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a term to search: ");
        String term = scanner.nextLine();
        scanner.close();
        Map<Integer, Integer> results = searcher.search(term);
        System.out.println("Documents containing the term '" + term + "': " + results);
    }

    private static Set<String> loadStopWords(String filePath) throws IOException {
        Set<String> stopWords = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stopWords.add(line.trim());
            }
        }
        return stopWords;
    }

    private static String parseTextContent(String content) {
        // Simple parser to extract text between <TEXT> tags
        int start = content.indexOf("<TEXT>");
        int end = content.indexOf("</TEXT>");
        if (start != -1 && end != -1) {
            return content.substring(start + 6, end).trim();
        }
        return "";
    }
}
