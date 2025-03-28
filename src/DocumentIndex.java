import java.io.*;
import java.util.*;

public class DocumentIndex {
    private Map<Integer, Map<String, Integer>> docIndex;

    public DocumentIndex() {
        docIndex = new HashMap<>();
    }

    public void addDoc(int docID, List<String> terms) {
        Map<String, Integer> termFrequency = new HashMap<>();
        for (String term : terms) {
            termFrequency.put(term, termFrequency.getOrDefault(term, 0) + 1);
        }
        docIndex.put(docID, termFrequency);
    }

    public void writeToFile(String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<Integer, Map<String, Integer>> entry : docIndex.entrySet()) {
                writer.write(entry.getKey() + ": ");
                for (Map.Entry<String, Integer> termEntry : entry.getValue().entrySet()) {
                    writer.write(termEntry.getKey() + ": " + termEntry.getValue() + "; ");
                }
                writer.newLine();
            }
        }
    }
}