package ngrams;

import edu.princeton.cs.algs4.In;
import net.sf.saxon.functions.Minimax;

import javax.print.attribute.standard.MediaSize;
import java.sql.Time;
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

    // TODO: Add any necessary static/instance variables.
//    public TimeSeries ListofWordswithYears;
//    public TimeSeries ListofYearswithWordcounts;
//    public TimeSeries ListofYearswithTotalWords;
//    public HashMap<Double,TimeSeries> HashStorageforTimeSeries;
//    public HashMap<Double,String> HashStorage;

    /*
     * Need to make a List that is able to hold multiple TimeSeries. Something like an Array List or have a Root branch
     * towards multiple branches. Timeseries consists of Year and Word Data, meaning it can fit right in.
     * We make a list named after the word so we can call it, but we use HastCode to make it easier.
     * We could use Set to make it as each year is unique per word.
     */

    public TreeMap<String, TimeSeries> WordStorage;
    public TimeSeries yearsWithCounts;
    public int MinYear;
    public int MaxYear;
    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        // TODO: Fill in this constructor. See the "NGramMap Tips" section of the spec for help.
        In wordFile = new In(wordsFilename); // Does file things (Gets items of file)
        MinYear = TimeSeries.MIN_YEAR;
        MaxYear = TimeSeries.MAX_YEAR;
        WordStorage = new TreeMap<>();
        while (wordFile.hasNextLine()) {
            String nextLine = wordFile.readLine(); // Gets data
            String[] splitLine = nextLine.split("\t"); // Converts data to callable items
            // Parse and store data
            String word = splitLine[0];
            int year = Integer.parseInt(splitLine[1]);
            double count = Double.parseDouble(splitLine[2]);

            // make sure there is a mapping between word and timeseries
            if (!WordStorage.containsKey(word)) { // Checks if word doesn't exist and adds it with new word.
                WordStorage.put(word, new TimeSeries());
            }
            WordStorage.get(word).put(year, count);
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
        // TODO: Fill in this method.
        if (!WordStorage.containsKey(word)) {
            return new TimeSeries();
        }
        TimeSeries DifL = WordStorage.get(word);
        TimeSeries Result = new TimeSeries();
        for (int year = startYear; year <= endYear; year ++) {
            if (DifL.containsKey(year)) {
                Result.put(year, DifL.get(year));
            }
        }
        return Result;
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        // TODO: Fill in this method.

        return countHistory(word,MinYear,MaxYear);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        // TODO: Fill in this method.
        return (TimeSeries) yearsWithCounts.clone();

    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        if (!WordStorage.containsKey(word)) {
            return new TimeSeries();
        }

        TimeSeries Result = new TimeSeries();
        TimeSeries DifL = WordStorage.get(word);
        for (int year = startYear; year <= endYear; year ++) {
            if (DifL.containsKey(year) && yearsWithCounts.containsKey(year)) {
                Result.put(year, DifL.get(year) / yearsWithCounts.get(year));
            }
        }
//
//        double total = 0.0;
//
//        TimeSeries DifL = WordStorage.get(word);
//        for (int year = MinYear; year <= MaxYear; year ++ ) {
//            if (DifL.containsKey(year)) {
//                total += DifL.get(year);
//            }
//        }
//        for (int year = startYear; year <= endYear; year ++ ) {
//            if (DifL.containsKey(year)) {
//                double rate = DifL.get(year);
//                Result.put(year, rate / total);
//            }
//        }
        return Result;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        // TODO: Fill in this method.

        return weightHistory(word,MinYear,MaxYear);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words, int startYear, int endYear) {
        // TODO: Fill in this method.
        double summed = 0.0;
        TimeSeries Result = new TimeSeries();
        for (String w : words) {
            if (!WordStorage.containsKey(w)) {
                return new TimeSeries();
            }

            TimeSeries Other = new TimeSeries();
            TimeSeries DifL = WordStorage.get(w);
            for (int year = startYear; year <= endYear; year++) {
                if (DifL.containsKey(year) && yearsWithCounts.containsKey(year)) {
                    if (Result.containsKey(year)) {
                        Other.put(year, DifL.get(year) / yearsWithCounts.get(year));
                        Result.plus(Other);
                        Other.clear();
                    } else {
                        Result.put(year, DifL.get(year) / yearsWithCounts.get(year));
                    }
                }
            }
        }
        return Result;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        // TODO: Fill in this method.

        return summedWeightHistory(words, MinYear, MaxYear);
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
