package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;

public class HamiltonianCycleStuff {
    private final List<Edge> graph;
    private int numberOfNodes;
    private final ExecutorService executor;

    public HamiltonianCycleStuff(List<Edge> graph) {
        this.graph = graph;
        this.executor = Executors.newWorkStealingPool();
    }

    public static List<Edge> readGraphFromFile(String fileName) throws FileNotFoundException {
        List<Edge> result = new ArrayList<>();

        File fileToRead = Paths.get("src", "main", "resources", fileName).toFile();
        Scanner scanner = new Scanner(fileToRead);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] lineParts = line.split(" ");
            Edge newEdge = new Edge(Integer.parseInt(lineParts[0]), Integer.parseInt(lineParts[1]));
            result.add(newEdge);
        }
        return result;
    }

    public void findHamiltonianCycle() throws ExecutionException, InterruptedException {
        List<Integer> path = new ArrayList<>();
        int start = graph.get(0).from;
        path.add(start);
        List<Integer> cycle = findHamiltonianCycleRecursive(path);
        if (cycle == null)
            System.out.println("No cycle found!!!");
        else
            System.out.println("Cycle found!: " + printCycle(cycle));
    }

    private String printCycle(List<Integer> cycle) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");
        int i = 0;
        for (int a : cycle) {
            if (a == cycle.get(cycle.size() - 1) && i > 0) {
                stringBuilder.append(i).append(")Node: ").append(a);
            } else {
                stringBuilder.append(i).append(")Node: ").append(a).append(" ->\n");
                i++;
            }
        }
        return stringBuilder.toString();
    }

    public int getGraphNumberOfNodes() {
        if (numberOfNodes != 0)
            return numberOfNodes;
        Set<Integer> checked = new HashSet<>();
        for (Edge e : graph) {
            checked.add(e.from);
            checked.add(e.to);
        }
        numberOfNodes = checked.size();
        return numberOfNodes;
    }

    private List<Integer> findHamiltonianCycleRecursive(List<Integer> path) throws ExecutionException, InterruptedException {
        if (path.size() == getGraphNumberOfNodes()) {
            boolean edgeToStartExists = graph.stream().anyMatch(e -> e.from == path.get(path.size() - 1) && e.to == path.get(0));
            if (edgeToStartExists) {
                path.add(path.get(0));
                return path;
            }
            return null;
        }

        List<Future<List<Integer>>> futures = new ArrayList<>();
        List<Edge> allEdgesForNode = graph.stream().filter(e -> e.from == path.get(path.size() - 1)).toList();
        for (Edge e : allEdgesForNode) {
            if (!path.contains(e.to)) {
                List<Integer> newPath = new ArrayList<>(path);
                newPath.add(e.to);
                futures.add(executor.submit(() -> findHamiltonianCycleRecursive(newPath)));
            }
        }

        for (Future<List<Integer>> f : futures) {
            List<Integer> result = f.get();
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    public static List<Edge> getRandomHamiltonianGraph(int numberOfNodes) {
        List<Integer> nodes = new ArrayList<>();
        for (int i = 1; i <= numberOfNodes; i++) {
            nodes.add(i);
        }
        List<Edge> result = new ArrayList<>();
        Random random = new Random();
        int iteration = 1;
        int fromNode = 1;
        nodes.remove(0);
        while (iteration < numberOfNodes) {
            iteration++;
            int randomA = random.nextInt(nodes.size());
            int randomB = random.nextInt(nodes.size());
            int randomC = random.nextInt(nodes.size());
            Edge a = new Edge(fromNode, nodes.get(randomA));
            Edge b = new Edge(fromNode, nodes.get(randomB));
            Edge c = new Edge(fromNode, nodes.get(randomC));
            result.add(a);
            result.add(b);
            result.add(c);
            fromNode = nodes.get(randomA);
            nodes.remove(randomA);
        }
        Edge finalEdge = new Edge(fromNode, 1);
        result.add(finalEdge);
        return result;
    }

    public static List<Edge> getRandomGraph(int numberOfNodes) {
        List<Integer> nodes = new ArrayList<>();
        for (int i = 1; i <= numberOfNodes; i++) {
            nodes.add(i);
        }
        Random random = new Random();
        List<Edge> result = new ArrayList<>();
        int total = numberOfNodes * 3;
        for (int i = 1; i <= total; i++)
        {
            int a,b;
            a = nodes.get(random.nextInt(nodes.size()));
            do {
                 b = nodes.get(random.nextInt(nodes.size()));
            }while(a!=b);
            Edge edge = new Edge(a,b);
            result.add(edge);
        }
        return result;
    }
}
