package com.github.bael;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Knapsack {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int W = sc.nextInt();

        Item[] items = new Item[n];
        for (int i = 0; i < n; i++) {
            items[i] = new Item(sc.nextInt(), sc.nextInt());
        }

        double max = knapsackCalc(W, items);
        System.out.println(max);
    }

    private static void test() {
        Item[] items = new Item[] {
                new Item(60, 20),
                new Item(100, 50),
                new Item(120, 30)
        };

        System.out.println(knapsackCalc(50, items));
    }

    private static double knapsackCalc(int W, Item[] items) {
        Arrays.sort(items, Comparator.reverseOrder());

        double takenAmount = 0;

        for(int i=0; i<items.length && W > 0; i++) {
            Item item = items[i];
            // если предмет поместился целиком
            if (item.w < W)  {
                takenAmount += item.c;
                W -= item.w;
            } else {
                takenAmount += item.getCost(W);
                W = 0;
            }
        }

        return takenAmount;
    }

    private static class Item implements Comparable<Item> {
        public Item(int c, int w) {
            this.c = c;
            this.w = w;
            assert w != 0;
        }

        private int c;
        private int w;
        public double getCost(int partialWeight) {
            return getPriority() * partialWeight;
        }
        public double getPriority() {
            return (double) c / w;
        }

        @Override
        public int compareTo(Item o) {
            return Double.compare(getPriority(), o.getPriority());
        }
    }


}
