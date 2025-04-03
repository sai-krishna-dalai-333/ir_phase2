import java.util.Map;

public class Searcher {
    private TermIndex index;

    public Searcher(TermIndex index) {
        this.index = index;
    }

    // Method to retrieve information based on a query term
    public Map<Integer, Integer> search(String queryTerm) {
        return index.find(queryTerm);
    }
}