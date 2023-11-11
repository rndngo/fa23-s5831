package main;

import edu.princeton.cs.algs4.In;

import java.util.*;

public class WordNet {
    private Graph graph;
    private HashMap<Integer, ArrayList<String>> numberToWords;
    private HashMap<String, ArrayList<Integer>> wordToNumbers;

    private static void addValue(HashMap<String, ArrayList<Integer>> multimap, String key, Integer value) {
        ArrayList<Integer> values = multimap.get(key);
        if (values == null) {
            values = new ArrayList<>();
        }
        values.add(value);
        multimap.put(key, values);

    }


    public WordNet(String synsetsFile, String hyponymsFile) {
        // populate word storage
        numberToWords = new HashMap<>();
        wordToNumbers = new HashMap<>();
        In syn = new In(synsetsFile);
        while (syn.hasNextLine()) {
            String wordline = syn.readLine();
            String[] mapping = wordline.split(",");
            // getting the words at this line
            ArrayList<String> words = new ArrayList<>(Arrays.asList(mapping[1].split(" ")));
            numberToWords.put(Integer.valueOf(mapping[0]), words);
            for (String word : words) {
                addValue(wordToNumbers, word, Integer.valueOf(mapping[0]));
            }
        }
        graph = new Graph(hyponymsFile);
    }

    public ArrayList<String> getHyponyms(String word, int k, double endYear, double startYear) {
        // returns an ordered list of the hyponyms of the word
        if (wordToNumbers.get(word) == null) {
            ArrayList<String> single = new ArrayList<>();
            single.add(word);
            return single;
        }
        // getting nodes
        ArrayList<Integer> nums = graph.getSubGraphNodes(wordToNumbers.get(word), k, endYear, startYear);

        // adding words to a set
        HashSet<String> unorderedHyponyms = new HashSet<>();
        for (Integer num : nums) {
            unorderedHyponyms.addAll(numberToWords.get(num));
        }

        // convert set to array, order array
        ArrayList<String> answer = new ArrayList<>(unorderedHyponyms);
        // ordering array
        answer.sort(null);
        return answer;

    }
}
