package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        StringBuilder stringBuilder = new StringBuilder();
        long time = System.currentTimeMillis();
        stringBuilder.append("Trying out hamilton cycle search for graph from file.. \n");
        List<Edge> graph = HamiltonianCycleStuff.readGraphFromFile("g1.in");
        HamiltonianCycleStuff hamiltonianCycleStuff = new HamiltonianCycleStuff(graph);
        hamiltonianCycleStuff.findHamiltonianCycle();
        stringBuilder.append("found cycle in "+ (System.currentTimeMillis() - time) + "ms\n\n");
        System.out.println(System.currentTimeMillis() - time + "ms");

        time = System.currentTimeMillis();
        stringBuilder.append("Trying out hamilton cycle search for bad graph with 50 vertices and 150 edges.. \n");
        List<Edge> graphBad1 = HamiltonianCycleStuff.getRandomGraph(50);
        HamiltonianCycleStuff hamiltonianCycleStuffBad1 = new HamiltonianCycleStuff(graphBad1);
        hamiltonianCycleStuffBad1.findHamiltonianCycle();
        stringBuilder.append("didnt find cycle in "+ (System.currentTimeMillis() - time) + "ms\n\n");
        System.out.println(System.currentTimeMillis() - time + "ms");

        time = System.currentTimeMillis();
        stringBuilder.append("Trying out hamilton cycle search for bad graph with 100 vertices and 300 edges.. \n");
        List<Edge> graphBad2 = HamiltonianCycleStuff.getRandomGraph(100);
        HamiltonianCycleStuff hamiltonianCycleStuffBad2 = new HamiltonianCycleStuff(graphBad2);
        hamiltonianCycleStuffBad2.findHamiltonianCycle();
        stringBuilder.append("didnt find cycle in "+ (System.currentTimeMillis() - time) + "ms\n\n");
        System.out.println(System.currentTimeMillis() - time + "ms");


        time = System.currentTimeMillis();
        stringBuilder.append("Trying out hamilton cycle search for good graph with 50 vertices and 151 edges.. \n");
        List<Edge> graph1 = HamiltonianCycleStuff.getRandomHamiltonianGraph(50);
        HamiltonianCycleStuff hamiltonianCycleStuff1 = new HamiltonianCycleStuff(graph1);
        hamiltonianCycleStuff1.findHamiltonianCycle();
        stringBuilder.append("found cycle in "+ (System.currentTimeMillis() - time) + "ms\n\n");
        System.out.println(System.currentTimeMillis() - time + "ms");

        time = System.currentTimeMillis();
        stringBuilder.append("Trying out hamilton cycle search for good graph with 100 vertices and 301 edges.. \n");
        List<Edge> graph2 = HamiltonianCycleStuff.getRandomHamiltonianGraph(100);
        HamiltonianCycleStuff hamiltonianCycleStuff2 = new HamiltonianCycleStuff(graph2);
        hamiltonianCycleStuff2.findHamiltonianCycle();
        stringBuilder.append("found cycle in "+ (System.currentTimeMillis() - time) + "ms\n\n");
        System.out.println(System.currentTimeMillis() - time + "ms");

        time = System.currentTimeMillis();
        stringBuilder.append("Trying out hamilton cycle search for good graph with 200 vertices and 601 edges.. \n");
        List<Edge> graph3 = HamiltonianCycleStuff.getRandomHamiltonianGraph(200);
        HamiltonianCycleStuff hamiltonianCycleStuff3 = new HamiltonianCycleStuff(graph3);
        hamiltonianCycleStuff3.findHamiltonianCycle();
        stringBuilder.append("found cycle in "+ (System.currentTimeMillis() - time) + "ms\n\n");
        System.out.println(System.currentTimeMillis() - time + "ms");

        time = System.currentTimeMillis();
        stringBuilder.append("Trying out hamilton cycle search for good graph with 300 vertices and 901 edges.. \n");
        List<Edge> graph4 = HamiltonianCycleStuff.getRandomHamiltonianGraph(300);
        HamiltonianCycleStuff hamiltonianCycleStuff4 = new HamiltonianCycleStuff(graph4);
        hamiltonianCycleStuff4.findHamiltonianCycle();
        stringBuilder.append("found cycle in "+ (System.currentTimeMillis() - time) + "ms\n\n");
        System.out.println(System.currentTimeMillis() - time + "ms");

        time = System.currentTimeMillis();
        stringBuilder.append("Trying out hamilton cycle search for good graph with 500 vertices and 1501 edges.. \n");
        List<Edge> graph5 = HamiltonianCycleStuff.getRandomHamiltonianGraph(500);
        HamiltonianCycleStuff hamiltonianCycleStuff5 = new HamiltonianCycleStuff(graph5);
        hamiltonianCycleStuff5.findHamiltonianCycle();
        stringBuilder.append("found cycle in "+ (System.currentTimeMillis() - time) + "ms\n\n");
        System.out.println(System.currentTimeMillis() - time + "ms");

        File outfile = Paths.get("src","main","resources","results.out").toFile();
        outfile.createNewFile();
        FileWriter fileWriter = new FileWriter(outfile);
        fileWriter.write(stringBuilder.toString());
        fileWriter.close();
    }
}