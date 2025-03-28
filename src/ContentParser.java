import java.io.*;
import java.util.*;
import java.util.regex.*;

public class ContentParser {
    private Set<String> stopWords;
    private Porter stemmer;

    public ContentParser(Set<String> stopWords, Porter stemmer) {
        this.stopWords = stopWords;
        this.stemmer = stemmer;
    }

    public List<String> parse(String content) {
        List<String> terms = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\w+");
        Matcher matcher = pattern.matcher(content.toLowerCase());

        while (matcher.find()) {
            String term = matcher.group();
            if (!stopWords.contains(term)) {
                terms.add(stemmer.stripAffixes(term));
            }
        }
        return terms;
    }
}