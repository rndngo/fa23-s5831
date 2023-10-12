package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import org.knowm.xchart.XYChart;
import plotting.Plotter;

import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends NgordnetQueryHandler {
    private NGramMap listofMaps;
    public HistoryHandler(NGramMap map) {
        listofMaps = map;
    }
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        ArrayList<TimeSeries> lts = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        for (String w : words) {
            labels.add(w);
            lts.add(listofMaps.weightHistory(w, startYear, endYear));
        }


        XYChart chart = Plotter.generateTimeSeriesChart(labels, lts);
        String encodedImage = Plotter.encodeChartAsString(chart);

        return encodedImage;
    }
}
