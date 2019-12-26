package com.github.bael;

import java.util.Arrays;
import java.util.Scanner;

public class StairwayDynProgramming {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int count = sc.nextInt();
        int[] numbers = new int[count];

        for (int i=0; i<count; i++) {
            numbers[i] = sc.nextInt();
        }

        System.out.println(maxSum(numbers));
    }

    private static long maxSum(int[] numbers) {
        long[] D = new long[numbers.length];
        Arrays.fill(D, Long.MIN_VALUE);

        return maxSumRec(numbers, 0, D);

    }

    private static long maxSumRec(int[] numbers, int i, long[] D) {
        if (i == numbers.length - 1) {
            return numbers[numbers.length - 1];
        } else {
            if (i >= numbers.length) {
                return 0;
            }
        }
        if (D[i] != Long.MIN_VALUE) {
            return D[i];
        }

        D[i] = Math.max(numbers[i] + maxSumRec(numbers, i + 1, D),
                numbers[i+1] + maxSumRec(numbers, i + 2, D));
        return D[i];
    }

}
