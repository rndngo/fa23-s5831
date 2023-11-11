package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class HyponymsHandler extends NgordnetQueryHandler {

    private WordNet listofMaps;
    private NGramMap ngm;
    public HyponymsHandler(WordNet map, NGramMap ngm) {
        listofMaps = map;
        this.ngm = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        int k = q.k();


        // create a list of sets. Where each set is the set of hyponyms of a word
        List<Set<String>> allHyponyms = new ArrayList<>();
        for (String word : words) {
            // function call to get the hyponyms of a word
            ArrayList<String> hypoLst = listofMaps.getHyponyms(word, q.k(), q.endYear(), q.startYear());
            Set<String> hypoSet = new HashSet<>(hypoLst);
            allHyponyms.add(hypoSet);
        }

        // finding the intersection of all the sets
        Set<String> intersection = new HashSet<>(allHyponyms.get(0)); // Start with the first set
        for (Set<String> set : allHyponyms) {
            intersection.retainAll(set); // Retain only common elements
        }

        // turing set to list and sorting
        ArrayList<String> hypoIntersection = new ArrayList<>(intersection);
        if (q.k() == 0) {
            hypoIntersection.sort(null);
            return hypoIntersection.toString();
        }

        // for each of the words, we need a mapping between word and count for the year range
        HashMap<String, Double> mapping = new HashMap<>();
        for (String word : hypoIntersection) {
            TimeSeries ts = ngm.countHistory(word, startYear, endYear);
            Collection<Double> yearCounts = ts.values();
            // Initialize the sum
            double sum = 0.0;

            // Iterate through the collection and accumulate the sum
            for (Double occurrenceCount : yearCounts) {
                sum += occurrenceCount;
            }
            mapping.put(word, sum);
        }

        // pick the top k based on count
        // Sort the 'mapping' entries by values in descending order
        List<String> sortedWords = mapping.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .toList();

        // Create a new List to store the top k words
         // Set your desired value for k
        List<String> topKWords = new ArrayList<>();

        // Iterate through the sorted words and add the top k words
        for (int i = 0; i < k && i < sortedWords.size(); i++) {
            topKWords.add(sortedWords.get(i));
        }

        topKWords.sort(null);

        return topKWords.toString();
    }

}
