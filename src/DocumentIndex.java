
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocumentIndex {
    private Map<Integer, Map<String, Integer>> index;

    public DocumentIndex() {
        index = new HashMap<>();
    }

    public void addDoc(int documentId, List<String> terms) {
        Map<String, Integer> frequencyMap = new HashMap<>();
        terms.forEach(term -> frequencyMap.put(term, frequencyMap.getOrDefault(term, 0) + 1));
        index.put(documentId, frequencyMap);
    }

    public void writeToFile(String filePath) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
            index.forEach((documentId, termMap) -> {
                try {
                    bufferedWriter.write(documentId + ": ");
                    termMap.forEach((term, frequency) -> {
                        try {
                            bufferedWriter.write(term + ": " + frequency + "; ");
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
}