
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TermIndex {
    private Map<String, Map<Integer, Integer>> index;

    public TermIndex() {
        index = new HashMap<>();
    }

    public void addDoc(int documentId, List<String> terms) {
        terms.forEach(term -> {
            index.computeIfAbsent(term, k -> new HashMap<>());
            Map<Integer, Integer> docFreqMap = index.get(term);
            docFreqMap.merge(documentId, 1, Integer::sum);
        });
    }

    public void writeToFile(String filePath) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
            index.forEach((term, docMap) -> {
                try {
                    bufferedWriter.write(term + ": ");
                    docMap.forEach((docId, frequency) -> {
                        try {
                            bufferedWriter.write(docId + ": " + frequency + "; ");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    bufferedWriter.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public Map<Integer, Integer> find(String term) {
        return index.getOrDefault(term, Collections.emptyMap());
    }
}