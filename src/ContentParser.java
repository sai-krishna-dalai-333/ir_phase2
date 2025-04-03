
import java.util.*;
import java.util.regex.*;

public class ContentParser {
    private Set<String> stops;
    private Porter stems;

    public ContentParser(Set<String> stops, Porter stems) {
        this.stops = stops;
        this.stems = stems;
    }

    public List<String> parse(String content) {
        List<String> terms = new LinkedList<>();
        Pattern termPattern = Pattern.compile("\\w+");
        Matcher termMatcher = termPattern.matcher(content.toLowerCase());

        while (termMatcher.find()) {
            String term = termMatcher.group();
            if (!stops.contains(term)) {
                String stemmedTerm = stems.stripAffixes(term);
                terms.add(stemmedTerm);
            }
        }
        return terms;
    }
}