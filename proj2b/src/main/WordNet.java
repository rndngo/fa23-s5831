package main;

import edu.princeton.cs.algs4.In;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.*;

public class WordNet {
    private Graph graph;
    private HashMap<Integer, ArrayList<String>> numberToWords;
    private HashMap<String, ArrayList<Integer>> wordToNumbers;

    private static void addValue(HashMap<String, ArrayList<Integer>> multimap, String key, Integer value) {
        ArrayList<Integer> values = multimap.get(key);
        if (values == null) {
            values = new ArrayList<>();
        }
        values.add(value);
        multimap.put(key, values);

    }


    public WordNet(String synsetsFile, String hyponymsFile) {
        // populate word storage
        numberToWords = new HashMap<>();
        wordToNumbers = new HashMap<>();
        In syn = new In(synsetsFile);
        while (syn.hasNextLine()) {
            String wordline = syn.readLine();
            String[] mapping = wordline.split(",");
            // getting the words at this line
            ArrayList<String> words = new ArrayList<>(Arrays.asList(mapping[1].split(" ")));
            numberToWords.put(Integer.valueOf(mapping[0]), words);
            for (String word : words) {
                addValue(wordToNumbers, word, Integer.valueOf(mapping[0]));
            }
        }
        graph = new Graph(hyponymsFile);
    }

    public ArrayList<String> getHyponyms(String word, int k, double endYear, double startYear) {
        // returns an ordered list of the hyponyms of the word

        // getting nodes
        ArrayList<Integer> nums = graph.getSubGraphNodes(wordToNumbers.get(word), k, endYear, startYear);

        // adding words to a set
        HashSet<String> unorderedHyponyms = new HashSet<>();
        for (Integer num : nums) {
            unorderedHyponyms.addAll(numberToWords.get(num));
        }

        // convert set to array, order array
        ArrayList<String> answer = new ArrayList<>(unorderedHyponyms);
        // ordering array
        answer.sort(null);
        return answer;

//
//        HashMap<Double, String> greatest = new HashMap<>();
//        ArrayList<String> answer = new ArrayList<>(unorderedHyponyms);
//        if (k > 0) {
//            ArrayList<Double> heighted = new ArrayList<>();
//            for (String w : answer) {
//                TimeSeries history = ngm.countHistory();
//                Double totalData = 0.0;
//                for (Double data : history.data()) {
//                    totalData += data;
//                    System.out.println(data);
//
//                }
//                greatest.put(totalData, w);
//                heighted.add(totalData);
//            }
//            heighted.sort(null);
//            ArrayList<String> hAnswer = new ArrayList<>();
//            System.out.println(heighted);
//            System.out.println(greatest);
//            for (int i = 0; i < k; i++) {
//                hAnswer.add(greatest.get(heighted.get(i)));
//            }
//            hAnswer.sort(null);
//            return hAnswer;
//        } else {
//            answer.sort(null);
//        }
//        return answer;
    }
}
//    public WordNet(String file1, String file2) {
//
//        // populate word storage
//        wordstorage = new HashMap<>();
//        In syn = new In(file1);
//        while (syn.hasNextLine()) {
//            String wordline = syn.readLine();
//            String[] splitline = wordline.split(",");
//            String[] split = splitline[1].split(" ");
//            for (String x : split) {
//                Node h = new Node(x, splitline[2]);
//                wordstorage.put(Integer.valueOf(splitline[0]), h);
//            }}
//
//        // populate connection storage
//        connectionstorage = new HashMap<>();
//        In hyp = new In(file2);
//        while (hyp.hasNextLine()) {
//            String numberline = hyp.readLine();
//            String[] splitline = numberline.split(",");
//            ArrayList<String> c = new ArrayList<>(Arrays.asList(splitline).subList(1, splitline.length));
//            connectionstorage.put(Integer.valueOf(splitline[0]), c);
//        }
//
//        // graph it
//        graph = new Graph(wordstorage, connectionstorage);
//    }
//}



//    private Graph graph;
//    // Wrapper for a graph
//    private ArrayList<Node> wordstorage;
//    private ArrayList<Node> connections;
//
//private class Node {
//    public Object key;
//    public Object value;
//    public Node(Object key, Object value) {
//        this.key = value;
//        this.value = key;
//    }
//}
//
////    Comment for myself: look at the proj2a and do what was done to get the items from the file. OFC it should be similar
////    and I'm pretty sure it would break if i sued the same code.
//
//    public WordNet(String wordFileName, String connectionFileName) {
//        graph = new Graph();
//        convertAllItems(wordFileName);
//        connectItems(connectionFileName);
//        // Build Graph -- > Add all edges
//    }
//
//    // Goes through the file and connects stuff that should be connected
//    // If connected then skip or see if it has something else it is connected to.
//    private void connectItems(String file2) {
//
//        connections = new ArrayList<>();
//        In connectionfile = new In(file2);
//        while (connectionfile.hasNextLine()) {
//            String nextline = connectionfile.readLine();
//            String[] splitline = nextline.split(",");
//            ArrayList<String> connected = new ArrayList<>(Arrays.asList(splitline));
//            connected.remove(0);
//            Node root = new Node(splitline[0],connected);
//            connections.add(root);
//        }
//
//    }
//
//    private void convertAllItems(String file1) {
//        wordstorage = new ArrayList<>();
//        In wordFile = new In(file1); // Does file things (Gets items of file)
//        while (wordFile.hasNextLine()) {
//            String nextLine = wordFile.readLine(); // Gets data
//            String[] splitLine = nextLine.split(","); // Converts data to callable items
//            String[] splitWord = splitLine[1].split(" ");
//            ArrayList<Node> wordholder = new ArrayList<>();
//            for (String x : splitWord) {
//                Node word = new Node(x,splitLine[2]);
//                wordholder.add(word);
//            }
//            Node number = new Node(splitLine[0],wordholder);
//            wordstorage.add(number);
//        }
//    }
//
//    private void converttoGraph() {
//
//        for (Node x : connections) {
//
//        }
//
//    }
//
//    private Node getItem(String index) {
//        for (Node x : wordstorage) {
//            if (index.equals(x.key)) {
//                return (Node) x.value;
//            }
//        }
//        return null;
//    }
//
//    public void getConnections() {
//
//    }
//
//
//// Graph helper Functions
