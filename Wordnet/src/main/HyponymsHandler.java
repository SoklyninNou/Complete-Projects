package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.HyponymsGraph;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {
    private HyponymsGraph graph;
    private NGramMap ngm;

    public HyponymsHandler(HyponymsGraph hg, NGramMap nGramMap) {
        graph = hg;
        ngm = nGramMap;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        if (graph.getHyponymsGraph(words) == null) {
            return "[]";
        }
        int wordCount = q.k();
        String[] sortedHyponym = getMostPopular(graph.getHyponymsGraph(words), q.k(), q.startYear(), q.endYear());
        TreeSet<String> hyponymSet = new TreeSet<>();
        String hyponyms = "[";
        boolean firstWord = true;
        if (wordCount == 0 || wordCount > graph.getHyponymsGraph(words).size()) {
            if ((q.startYear() == 0 && q.endYear() == 0) || q.k() == 0) {
                hyponymSet = graph.getHyponymsGraph(words);
            } else {
                hyponymSet = yearFiler(graph.getHyponymsGraph(words), q.startYear(), q.endYear());
            }
            for (String word : hyponymSet) {
                if (firstWord) {
                    hyponyms += word;
                    firstWord = false;
                } else {
                    hyponyms += ", " + word;
                }
            }
        } else {
            int loopLimit = Math.min(wordCount, sortedHyponym.length);
            for (int i = 0; i < loopLimit; i++) {
                if (sortedHyponym[i].compareTo("") != 0) {
                    hyponymSet.add(sortedHyponym[i]);
                }
            }
            int counter = 0;
            for (String word : hyponymSet) {
                if (firstWord) {
                    hyponyms += word;
                    firstWord = false;
                } else {
                    hyponyms += ", " + word;
                }
                counter++;
                if (counter == wordCount) {
                    break;
                }
            }
        }
        hyponyms += "]";
        return hyponyms;
    }

    private String[] getMostPopular(TreeSet<String> hyponyms, int kCount, int startYear, int endYear) {
        HashMap<String, Double> wordCount = new HashMap<>();

        // Get the total counts for each word
        for (String word : hyponyms) {
            TimeSeries ts = ngm.countHistory(word, startYear, endYear);
            for (int year : ts.keySet()) {
                double count = ts.get(year);
                wordCount.put(word, wordCount.getOrDefault(word, 0.0) + count);
            }
        }

        String[] result = new String[kCount];
        // Sort words by their total counts in descending order and put them in result
        for (int i = 0; i < kCount; i++) {
            double maxCount = 0.0;
            String mostPopular = "";

            // Find the word with the highest count
            for (String word : wordCount.keySet()) {
                if (wordCount.get(word) > maxCount) {
                    mostPopular = word;
                    maxCount = wordCount.get(word);
                }
            }

            // Add the most popular word to the result
            result[i] = mostPopular;
            // Remove the most popular word from the wordCount map
            wordCount.remove(mostPopular);
        }

        return result;
    }

    private TreeSet<String> yearFiler(TreeSet<String> hyponymSet, int startYear, int endYear) {
        HashMap<String, Double> wordCount = new HashMap<>();
        for (String word : hyponymSet) {
            TimeSeries ts = ngm.countHistory(word, startYear, endYear);
            for (int year : ts.keySet()) {
                double count = ts.get(year);
                wordCount.put(word, wordCount.getOrDefault(word, 0.0) + count);
            }
        }
        TreeSet<String> result = new TreeSet<>();
        for (String word : wordCount.keySet()) {
            result.add(word);
        }
        return result;
    }

}
