package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.*;

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
    private final TreeMap<String, TimeSeries> wordStorage;
    private final TimeSeries yearsWithCounts;
    private final int minYear;
    private final int maxYear;
    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        In wordFile = new In(wordsFilename); // Does file things (Gets items of file)
        minYear = TimeSeries.MIN_YEAR;
        maxYear = TimeSeries.MAX_YEAR;
        wordStorage = new TreeMap<>();
        while (wordFile.hasNextLine()) {
            String nextLine = wordFile.readLine(); // Gets data
            String[] splitLine = nextLine.split("\t"); // Converts data to callable items
            // Parse and store data
            String word = splitLine[0];
            int year = Integer.parseInt(splitLine[1]);
            double count = Double.parseDouble(splitLine[2]);

            // make sure there is a mapping between word and timeseries
            if (!wordStorage.containsKey(word)) { // Checks if word doesn't exist and adds it with new word.
                wordStorage.put(word, new TimeSeries());
            }
            wordStorage.get(word).put(year, count);
        }
        wordFile.close();

        In countFile = new In(countsFilename); // Does file things (Gets items of file)
        yearsWithCounts = new TimeSeries();
        while (countFile.hasNextLine()) {
            String nextLine = countFile.readLine(); // Gets data
            String[] splitLine = nextLine.split(","); // Converts data to callable items
            // Parse and store data
            int year = Integer.parseInt(splitLine[0]);
            double count = Double.parseDouble(splitLine[1]);
            yearsWithCounts.put(year, count);
        }
        countFile.close();
    }


    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        if (!wordStorage.containsKey(word)) {
            return new TimeSeries();
        }
        TimeSeries difL = wordStorage.get(word);
        TimeSeries result = new TimeSeries();
        for (int year = startYear; year <= endYear; year++) {
            if (difL.containsKey(year)) {
                result.put(year, difL.get(year));
            }
        }
        return result;
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        return countHistory(word, minYear, maxYear);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        return (TimeSeries) yearsWithCounts.clone();
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        if (!wordStorage.containsKey(word)) {
            return new TimeSeries();
        }
        TimeSeries result = new TimeSeries();
        TimeSeries wordTs = wordStorage.get(word);
        for (int year = startYear; year <= endYear; year++) {
            if (wordTs.containsKey(year) && yearsWithCounts.containsKey(year)) {
                result.put(year, wordTs.get(year) / yearsWithCounts.get(year));
            }
        }
        return result;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        return weightHistory(word, minYear, maxYear);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words, int startYear, int endYear) {
        double summed = 0.0;
        TimeSeries r = new TimeSeries();
        for (String word : words) {
            if (!wordStorage.containsKey(word)) {
                continue;
            }
            r = r.plus(weightHistory(word, startYear, endYear));
        }
        return r;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        return summedWeightHistory(words, minYear, maxYear);
    }

}
