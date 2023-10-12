package ngrams;

import com.sun.source.tree.CaseTree;
import edu.princeton.cs.algs4.In;

import java.sql.Time;
import java.util.*;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    public static final int MIN_YEAR = 1400;
    public static final int MAX_YEAR = 2100;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        // TODO: Fill in this constructor.
        this.clear();
        for (int year = startYear; year <= endYear; year ++) {
            if (ts.containsKey(year)) {
                this.put(year,ts.get(year));
            }
        }
    }

    /**
     * Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        // TODO: Fill in this method.
        List<Integer> List = new ArrayList<>();
        Set<Integer> Years = this.keySet();
        for (Integer x : Years) {
            List.add(x);
        }

        return List.stream().toList();
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        // TODO: Fill in this method.
        Collection<Double> ListofYears = values();
        Double[] ListYears = new Double[this.size()];
        int c = 0;
        for (Double x : ListofYears) {
            ListYears[c] = x;
            c += 1;
        }

        return List.of(ListYears);
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     *
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        // TODO: Fill in this method.
        TimeSeries LY1 = this;
        Set<Integer> LY3 = ts.keySet();
        if (this.size() == 0 && ts.size() == 0) {
            return ts;
        }
        for (Integer x : LY3) {
            if (LY1.containsKey(x)) {
                Double newValue = ts.get(x) + LY1.get(x);
                LY1.replace(x, newValue);
            } else {
                LY1.put(x, ts.get(x));
            }
        }
        return LY1;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     *
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        // TODO: Fill in this method.
        TimeSeries LY1 = this;
        Set<Integer> LY2 = this.keySet();

        for (Integer x : LY2) {
            if (!ts.containsKey(x)) {
                throw new IllegalArgumentException();
            }
            Double newValue = LY1.get(x) / ts.get(x);
            LY1.replace(x, newValue);
        }


        return LY1;
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
