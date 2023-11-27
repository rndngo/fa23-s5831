package main;

import edu.princeton.cs.algs4.In;

import java.util.*;


public class Graph {
    private HashMap<Integer, ArrayList<Integer>> graph = new HashMap<>();

    public Graph(String aDAGFilename) {
        // creating graph of form {int: int[], ...}
        In aDAGFile = new In(aDAGFilename);
        while (aDAGFile.hasNextLine()) {
            String numberline = aDAGFile.readLine();
            String[] splitline = numberline.split(",");
            ArrayList<Integer> children = new ArrayList<>();
            // converting string list to integer list
            for (int i = 1; i < splitline.length; i++) {
                int intValue = Integer.parseInt(splitline[i]);
                children.add(intValue);
            }

            Integer parent = Integer.valueOf(splitline[0]);
            graph.put(parent, children);
        }
    }

    public ArrayList<Integer> getSubGraphNodes(ArrayList<Integer> nums, int k, double endYear, double startYear) {
        ArrayList<Integer> answer = new ArrayList<>();
        ArrayList<Integer> unexplored = new ArrayList<>(nums);
        while (!unexplored.isEmpty()) {
            Integer popped = unexplored.get(0);
            unexplored.remove(0);
            answer.add(popped);
            if (graph.get(popped) != null) {
                unexplored.addAll(graph.get(popped));
            }
        }
        return answer;
    }
}
