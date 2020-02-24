package com.github.bael.csprogram;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class JoinTableSimulationDemo {
    public static void main(String[] args) throws FileNotFoundException {

        String fileName = "src/main/resources/join_table_input.txt";
        Scanner sc = new Scanner(new File(fileName));
//        Scanner sc = new Scanner(System.in);
        new JoinTableSimulationDemo().runDemo(sc);
    }

    private void runDemo(Scanner sc) {
        int tableCount = sc.nextInt();
        int queryCount = sc.nextInt();
        int[] recordsCount = new int[tableCount];
        for (int i = 0; i < tableCount; i++) {
            recordsCount[i] = sc.nextInt();
        }

        int[][] joins = new int[queryCount][2];
        for (int i = 0; i < queryCount; i++) {
            joins[i][0] = sc.nextInt();
            joins[i][1] = sc.nextInt();
        }

        JoinTableSimulation simulation = new JoinTableSimulation(joins, recordsCount);
        simulation.applyJoins();

    }

    private static class JoinTableSimulation {

        int[][] joins;
        int[] recordsCount;
        int[] parent;
        int[] rank;
        int max = 0;

        public JoinTableSimulation(int[][] joins, int[] recordsCount) {
            this.joins = joins;
            this.recordsCount = recordsCount;
            parent = new int[recordsCount.length];
            rank = new int[recordsCount.length];
            for (int i = 0; i < parent.length; i++) {
                parent[i] = i;
                max = Math.max(recordsCount[i], max);
            }
        }

        int find(int i) {
            if (i != parent[i]) {
                parent[i] = find(parent[i]);
            }
            return parent[i];
//            while (i != tables[i]) {
//                i = tables[i];
//            }
//            return i;
        }

        void union(int i, int j) {
            int iRoot = find(i);
            int jRoot = find(j);
            if (iRoot == jRoot) {
                return;
            }
            // большое дерево поглощает маленькое
            if (rank[iRoot] > rank[jRoot]) {
                parent[jRoot] = iRoot;
                // симулирем перенос записей из маленького дерева в большое
                recordsCount[iRoot] += recordsCount[jRoot];
                // обновляем максимум
                max = Math.max(recordsCount[iRoot], max);
                recordsCount[jRoot] = 0;
            } else {
                parent[iRoot] = jRoot;
                // симулирем перенос записей из маленького дерева в большое
                recordsCount[jRoot] += recordsCount[iRoot];
                // обновляем максимум
                max = Math.max(recordsCount[jRoot], max);
                recordsCount[iRoot] = 0;
                if (rank[iRoot] == rank[jRoot]) {
                    rank[jRoot] += 1;
                }
            }

        }


        public void applyJoins() {
            for (int i = 0; i < joins.length; i++) {
                union(joins[i][0] - 1, joins[i][1] - 1);
                System.out.println(max);
            }
        }
    }
}
