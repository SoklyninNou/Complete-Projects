package ngrams;

import edu.princeton.cs.algs4.In;
import demo.HistoryTextHandler;

import java.util.*;

import static spark.Spark.get;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {


    private TimeSeries counts;
    private HashMap<String, TimeSeries> words;
    private boolean countsEmpty;
    private boolean wordsEmpty;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        In countsData = new In(countsFilename);
        In wordsData = new In(wordsFilename);
        countsEmpty = countsData.isEmpty();
        wordsEmpty = wordsData.isEmpty();
        counts = new TimeSeries();
        words = new HashMap<>();
        String[] cData = countsData.readAllLines();
        String[] wData = wordsData.readAllLines();
        for (String line : cData) {
            String[] part = line.split(",");
            counts.put(Integer.valueOf(part[0]), Double.valueOf(part[1]));
        }
        for (String line : wData) {
            String[] part = line.split("\t");
            if (!words.containsKey(part[0])) {
                words.put(part[0], new TimeSeries());
            }
            words.get(part[0]).put(Integer.valueOf(part[1]), Double.valueOf(part[2]));
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        TimeSeries ts = new TimeSeries(countHistory(word), startYear, endYear);
        return ts;
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        TimeSeries ts = new TimeSeries();
        if (wordsEmpty || !words.containsKey(word)) {
            return ts;
        }
        ts = words.get(word);
        return ts;
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        TimeSeries ts = new TimeSeries();
        if (countsEmpty) {
            return ts;
        }
        ts = counts;
        return ts;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries ts = new TimeSeries(weightHistory(word), startYear, endYear);
        return ts;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        TimeSeries ts = new TimeSeries();
        if (countsEmpty || wordsEmpty || !words.containsKey(word)) {
            return ts;
        }
        TimeSeries ts1 = countHistory(word);
        if (ts1.isEmpty()) {
            return ts;
        }
        TimeSeries ts2 = totalCountHistory();
        for (int year : ts1.keySet()) {
            if (ts1.get(year) != null && ts2.get(year) != null) {
                ts.put(year, ts1.get(year) / ts2.get(year));
            }
        }
        return ts;
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> wordsArray,
                                          int startYear, int endYear) {
        TimeSeries ts = new TimeSeries();
        for (String word : wordsArray) {
            TimeSeries wordWeightHistory = weightHistory(word, startYear, endYear);
            for (int year = startYear; year <= endYear; year++) {
                double currentValue = ts.getOrDefault(year, 0.0);
                double wordValue = wordWeightHistory.getOrDefault(year, 0.0);
                ts.put(year, currentValue + wordValue);
            }
        }
        return ts;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> wordsArray) {
        TimeSeries ts = new TimeSeries();
        for (String word : wordsArray) {
            TimeSeries wordWeightHistory = weightHistory(word);
            for (int year : wordWeightHistory.keySet()) {
                if (wordWeightHistory.get(year) != null) {
                    double currentValue = ts.getOrDefault(year, 0.0);
                    double wordValue = wordWeightHistory.get(year);
                    ts.put(year, currentValue + wordValue);
                }
            }
        }
        return ts;
    }
    public void register(String url, HistoryTextHandler nqh) {
        get(url, nqh);
    }
}
