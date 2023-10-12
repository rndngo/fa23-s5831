package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.sql.Time;
import java.util.List;
import java.util.TreeMap;

public class HistoryTextHandler extends NgordnetQueryHandler {
    public NGramMap ListofMaps;
    public HistoryTextHandler(NGramMap map) {
        ListofMaps = map;
    }
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        String response = "";
        for (String w : words) {
            TimeSeries Weighed =  ListofMaps.weightHistory(w,startYear,endYear);
            response += w + ": {";
            if (Weighed.containsKey(startYear)) {
                response += startYear + "=" + Weighed.get(startYear) + ", " ;
            }
            for (int year = startYear + 1; year <= endYear; year++) {
                if (Weighed.containsKey(year)) {
                    response +=  year + "=" + Weighed.get(year) + ", ";
                }
            }
            response = response.substring(0,response.length()-2);
            response += "}" + "\n";
        }
        return response;
    }
}
