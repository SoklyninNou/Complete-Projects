package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.*;

public class HyponymsGraph {
    private HashMap<String, ArrayList<Integer>> wordToIndexes;
    private HashMap<Integer, ArrayList<Integer>> adjList;
    private HashMap<Integer, ArrayList<String>> indexesToWords;
    private String[] hData;
    private String[] sData;
    private In hyponymsData;
    private In synsetsData;
    private int wordCount;
    public HyponymsGraph(String synsetsFile, String hyponymsFile) {
        hyponymsData = new In(hyponymsFile);
        hData = hyponymsData.readAllLines();

        synsetsData = new In(synsetsFile);
        sData = synsetsData.readAllLines();

        wordToIndexes = new HashMap<>();
        adjList = new HashMap<>();
        indexesToWords = new HashMap<>();
        wordsToIndex();
        initialConnections();
        indexToWords();
    }
    /**
     * Create a HashMap that maps all the indexes of words to the words
     *
     **/
    private void wordsToIndex() {
        for (String line : sData) {
            String[] part = line.split(",");
            String[] part2 = part[1].split(" ");
            for (String word : part2) {
                if (!wordToIndexes.containsKey(word)) {
                    wordToIndexes.put(word, new ArrayList<Integer>());
                }
                wordToIndexes.get(word).add(Integer.valueOf(part[0]));
            }
        }
    }
    /**
     * Create a HashMap that maps hyponyms to its words using the hyponym file
     * Uses recursion for depth-first search
     **/
    private void initialConnections() {
        for (String line : hData) {
            String[] parts = line.split(",");
            adjList.put(Integer.valueOf(parts[0]), new ArrayList<Integer>());
            for (String index : parts) {
                adjList.get(Integer.valueOf(parts[0])).add(Integer.valueOf(index));
            }
        }
    }

    /**
    * Create a HashMap that maps words to its index using the Synset file
    **/
    private void indexToWords() {
        for (String line : sData) {
            String[] part = line.split(",");
            String[] part2 = part[1].split(" ");
            indexesToWords.put(Integer.valueOf(part[0]), new ArrayList<String>());
            for (String word : part2) {
                indexesToWords.get(Integer.valueOf(part[0])).add(word);
            }
        }
    }

    private ArrayList<Integer> wordIndexes(String word) {
        return wordToIndexes.get(word);
    }

    private ArrayList<String> indexWords(int index) {
        return indexesToWords.get(index);
    }

    public TreeSet<String> getHyponymsGraph(String word) {
        TreeSet<String> treeSet = new TreeSet<>();
        ArrayList<Integer> allIndexes = wordIndexes(word);

        // For each index corresponding to the word, find all descendants
        for (int index : allIndexes) {
            Set<Integer> children = getAllChildren(index);
            for (int child : children) {
                treeSet.addAll(indexWords(child)); // Convert each descendant index to words
            }
        }

        return treeSet;
    }


    public TreeSet<String> getHyponymsGraph(List<String> words) {
        TreeSet<String> resultSet = new TreeSet<>();
        boolean isFirstWord = true;

        // Iterate through each word in the list
        for (String word : words) {
            Set<String> currentHyponyms = new TreeSet<>();

            // Get all indexes associated with this word
            ArrayList<Integer> wordIndexes = wordIndexes(word);
            // Skip if the indexes for word not found
            if (wordIndexes == null) {
                return new TreeSet<>();
            }
            for (int index : wordIndexes) {
                // For each index, get all descendant indexes
                Set<Integer> children = getAllChildren(index);
                // Convert descendant indexes to words and add to currentHyponyms set
                for (int child : children) {
                    currentHyponyms.addAll(indexWords(child));
                }
            }

            // For the first word, initialize the resultSet with its hyponyms
            if (isFirstWord) {
                resultSet.addAll(currentHyponyms);
                isFirstWord = false;
            } else {
                // For subsequent words, retain only the intersection of hyponyms
                resultSet.retainAll(currentHyponyms);
            }
        }

        return resultSet;
    }

    private Set<Integer> getAllChildren(int index) {
        Set<Integer> children = new HashSet<>(); // To store all reachable nodes
        dfs(index, children); // Start DFS from the given index
        return children;
    }

    private void dfs(int node, Set<Integer> visited) {
        if (!visited.contains(node)) {
            visited.add(node);
            if (adjList.containsKey(node)) {
                for (int child : adjList.get(node)) {
                    dfs(child, visited);
                }
            }
        }
    }

}
