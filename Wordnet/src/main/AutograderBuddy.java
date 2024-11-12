package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.HyponymsGraph;
import ngrams.NGramMap;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymsHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {

        HyponymsGraph hg = new HyponymsGraph(synsetFile, hyponymFile);
        NGramMap ngm = new NGramMap(wordFile, countFile);

        return new HyponymsHandler(hg, ngm);
    }
}
