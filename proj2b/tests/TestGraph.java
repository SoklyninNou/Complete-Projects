import browser.NgordnetQuery;
import ngrams.HyponymsGraph;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class TestGraph {
    public static final String SMALL_SYNSET_FILE = "data/wordnet/synsets16.txt";
    public static final String SMALL_HYPONYM_FILE = "data/wordnet/hyponyms16.txt";
    @Test
    public void test1() {
        HyponymsGraph graph = new HyponymsGraph(SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        ArrayList<String> words = new ArrayList<>();
        words.add("occurrence");
        words.add("change");

        System.out.println("[alteration, change, demotion, increase, jump, leap, modification, saltation, transition, variation]");
        System.out.println(graph.getHyponymsGraph("change"));
        System.out.println("[alteration, change, increase, jump, leap, modification, saltation, transition]");
        System.out.println(graph.getHyponymsGraph(words));

    }
}
