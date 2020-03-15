package com.github.bael.csprogram;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Проверка нарушения условия равенства переменных
 */
public class AnalyzeEqualsViolationDemo {
    public static void main(String[] args) throws FileNotFoundException {

        String fileName = "src/main/resources/equals_input.txt";
        Scanner sc = new Scanner(new File(fileName));
//        Scanner sc = new Scanner(System.in);
        new AnalyzeEqualsViolationDemo().runDemo(sc);
    }

    private void runDemo(Scanner sc) {
        int varCount = sc.nextInt();
        int eqCount = sc.nextInt();
        int notEqCount = sc.nextInt();

        UnionFind simulation = new UnionFind(varCount);
        for (int i = 0; i < eqCount; i++) {
            simulation.union(sc.nextInt() - 1, sc.nextInt() - 1);
        }

        int areEquals = 1;
        for (int i = 0; i < notEqCount; i++) {
            int l = sc.nextInt();
            int r = sc.nextInt();
            if (simulation.areInSameSet(l - 1, r - 1)) {
                areEquals = 0;
            }
        }

        System.out.println(areEquals);
    }

    private static class UnionFind {
        int[] parent;
        int[] rank;

        public UnionFind(int varsCount) {
            parent = new int[varsCount];
            rank = new int[varsCount];
            for (int i = 0; i < parent.length; i++) {
                parent[i] = i;
            }
        }

        int find(int i) {
            if (i != parent[i]) {
                parent[i] = find(parent[i]);
            }
            return parent[i];
        }

        boolean areInSameSet(int i, int j) {
            return find(i) == find(j);
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
            } else {
                parent[iRoot] = jRoot;
                if (rank[iRoot] == rank[jRoot]) {
                    rank[jRoot] += 1;
                }
            }
        }
    }
}
