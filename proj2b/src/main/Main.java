package main;

import browser.NgordnetServer;
import ngrams.NGramMap;

public class Main {
    public static void main(String[] args) {
        NgordnetServer hns = new NgordnetServer();
        

        String wordFile = "./data/ngrams/top_14377_words.csv";
        String countFile = "./data/ngrams/total_counts.csv";
//        NGramMap ngm = new NGramMap(wordFile, countFile);

        hns.startUp();
//        hns.register("history", new DummyHistoryHandler());
//        hns.register("historytext", new DummyHistoryTextHandler());
        hns.register("hyponyms", new HyponymsHandler());

        System.out.println("Finished server startup! Visit http://localhost:4567/ngordnet.html");
    }
}
