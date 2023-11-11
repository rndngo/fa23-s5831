package main;

import browser.NgordnetServer;
import ngrams.NGramMap;

public class Main {
    public static void main(String[] args) {
        NgordnetServer hns = new NgordnetServer();

        String wordFile = "./data/ngrams/top_14377_words.csv";
        String countFile = "./data/ngrams/total_counts.csv";
        String synsetsFile = "./data/wordnet/synsets.txt";
        String hyponymsFile = "./data/wordnet/hyponyms.txt";
        NGramMap ngm = new NGramMap(wordFile, countFile);

        WordNet wn = new WordNet(synsetsFile, hyponymsFile);
        // Need to connect the file that holds the data for this code

        hns.startUp();
        hns.register("history", new DummyHistoryHandler());
        hns.register("historytext", new DummyHistoryTextHandler());
        hns.register("hyponyms", new HyponymsHandler(wn, ngm));

        System.out.println("Finished server startup! Visit http://localhost:4567/ngordnet.html");
    }
}


//        String hyponymsFile = "./data/wordnet/hyponyms16.txt";
//
//        Graph graph = new Graph(hyponymsFile);
//
//        ArrayList<Integer> seeds = new ArrayList<>();
//        seeds.add(2);
//        System.out.print(graph.getSubGraphNodes(seeds));
