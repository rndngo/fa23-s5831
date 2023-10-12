package ngrams;


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
        this.clear();
        for (int year = startYear; year <= endYear; year++) {
            if (ts.containsKey(year)) {
                this.put(year, ts.get(year));
            }
        }
    }

    /**
     * Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        List<Integer> list = new ArrayList<>();
        Set<Integer> years = this.keySet();
        for (Integer x : years) {
            list.add(x);
        }

        return list.stream().toList();
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        Collection<Double> listofYears = values();
        Double[] listYears = new Double[this.size()];
        int c = 0;
        for (Double x : listofYears) {
            listYears[c] = x;
            c += 1;
        }

        return List.of(listYears);
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
        TimeSeries lY1 = new TimeSeries();
        TimeSeries lY2 = this;
        Set<Integer> lY3 = ts.keySet();
        if (this.size() == 0 && ts.size() == 0) {
            return ts;
        }
        lY1.putAll(lY2);
        for (Integer x : lY3) {
            if (lY2.containsKey(x)) {
                Double newValue = ts.get(x) + lY2.get(x);
                lY1.put(x, newValue);
            } else {
                lY1.put(x, ts.get(x));
            }
        }
        return lY1;
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
        TimeSeries lY1 = this;
        Set<Integer> lY2 = this.keySet();

        for (Integer x : lY2) {
            if (!ts.containsKey(x)) {
                throw new IllegalArgumentException();
            }
            Double newValue = lY1.get(x) / ts.get(x);
            lY1.replace(x, newValue);
        }


        return lY1;
    }

}
