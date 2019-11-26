package com.github.bael;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Change {
    public static void main(String[] args) {
        int sum = 74;
//        Integer[] nominal = new Integer[] {25, 10, 5, 1};
        Integer[] nominal = new Integer[] {25, 20, 12, 1};
        System.out.println(Arrays.toString(change(sum, nominal)));
    }

    private static int[] change(int sum, Integer[] nominal) {
        Arrays.sort(nominal, Comparator.reverseOrder());
        int[] changeByNominal = new int[nominal.length];
        for (int i=0; i<nominal.length && sum > 0; i++) {
            int count = sum / nominal[i];
            sum = sum % nominal[i];
            changeByNominal[i] = count;
        }
        return changeByNominal;
    }
}
