package com.github.bael;

import java.util.Scanner;

public class KnapsackDynProgramming {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int weight = sc.nextInt();
        int n = sc.nextInt();

        int[] bullion = new int[n];

        for (int i = 0; i < n; i++) {
            bullion[i] = sc.nextInt();
        }
//        int weight = 10;
//        int[] bull = new int[] {1, 4, 8};

        int maxWeight = new KnapsackDynProgramming().maxWeight(weight, bullion);
//        int maxWeight = new KnapsackDynProgramming().maxWeight(weight, bull);
        System.out.println(maxWeight);
    }

    private int maxWeight(int weight, int[] bullion) {
        int n = bullion.length;
        int[][] memo = new int[weight + 1][n + 1];
        for (int i = 0; i < weight; i++) {
            memo[i][0] = 0;
        }
        for (int i = 1; i <= n; i++) {
            for (int w = 1; w <= weight; w++) {
                memo[w][i] = memo[w][i - 1];
                int wi = bullion[i - 1];
                int ci = wi * 1;
                if (wi <= w) {
                    memo[w][i] = Math.max(memo[w][i], memo[w - wi][i - 1] + ci);
                }
            }
        }

        return memo[weight][n];
    }
}
