package main;

import edu.princeton.cs.algs4.In;
import org.apache.logging.log4j.util.SortedArrayStringMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class WordNet {
    private Graph graph;
    private HashMap<Integer, Node> wordstorage;
    private HashMap<Integer, ArrayList<String>> connectionstorage;

    private static class Node {
        String key;
        String value;

        public Node(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    public WordNet(String file1, String file2) {
        String hyponymsFile = "./data/wordnet/hyponyms16.txt";
        graph = new Graph(hyponymsFile);

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
