import java.util.Map;

public class Searcher {
    private TermIndex termIndex;

    public Searcher(TermIndex termIndex) {
        this.termIndex = termIndex;
    }

    // Method to retrieve information based on a query term
    public Map<Integer, Integer> search(String term) {
        return termIndex.find(term);
    }
}