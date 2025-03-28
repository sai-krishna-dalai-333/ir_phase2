import java.io.*;
import java.util.*;

public class TermIndex {
    private Map<String, Map<Integer, Integer>> termIndex;

    public TermIndex() {
        termIndex = new HashMap<>();
    }

    public void addDoc(int docID, List<String> terms) {
        for (String term : terms) {
            termIndex.putIfAbsent(term, new HashMap<>());
            Map<Integer, Integer> docFrequency = termIndex.get(term);
            docFrequency.put(docID, docFrequency.getOrDefault(docID, 0) + 1);
        }
    }

    public void writeToFile(String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, Map<Integer, Integer>> entry : termIndex.entrySet()) {
                writer.write(entry.getKey() + ": ");
                for (Map.Entry<Integer, Integer> docEntry : entry.getValue().entrySet()) {
                    writer.write(docEntry.getKey() + ": " + docEntry.getValue() + "; ");
                }
                writer.newLine();
            }
        }
    }

    public Map<Integer, Integer> find(String term) {
        return termIndex.getOrDefault(term, Collections.emptyMap());
    }
}