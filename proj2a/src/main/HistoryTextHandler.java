package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    private NGramMap listofMaps;
    public HistoryTextHandler(NGramMap map) {
        listofMaps = map;
    }
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        String response = "";
        for (String w : words) {
            TimeSeries weight =  listofMaps.weightHistory(w, startYear, endYear);
            response += w + ": {";
            if (weight.containsKey(startYear)) {
                response += startYear + "=" + weight.get(startYear) + ", ";
            }
            for (int year = startYear + 1; year <= endYear; year++) {
                if (weight.containsKey(year)) {
                    response +=  year + "=" + weight.get(year) + ", ";
                }
            }
            response = response.substring(0, response.length() - 2);
            response += "}" + "\n";
        }
        return response;
    }
}
