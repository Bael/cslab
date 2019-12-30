package com.github.bael;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiFunction;

public class BracketsDynProgramming {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
//        String line = sc.nextLine();
        String line = "5-8+7*4-8+9";

        char[] chars = line.toCharArray();
        // operations = digits - 1; line.length = operations + digits => digits = (line.length + 1) / 2
        int digitsCount = (chars.length + 1) / 2;
        int[] digits = new int[digitsCount];

        List<BiFunction<Integer, Integer, Integer>> operations = new ArrayList<>();
        for (int i = 0; i < chars.length; i++) {
            if (i % 2 != 0) {
                switch (chars[i]) {
                    case '*':
                        operations.add((integer, integer2) -> integer * integer2);
                        break;
                    case '-':
                        operations.add((integer, integer2) -> integer - integer2);
                        break;
                    case '+':
                        operations.add((integer, integer2) -> integer + integer2);
                        break;
                }
            } else {
                digits[(i + 1) / 2] = Integer.parseInt(String.valueOf(chars[i]));
            }
        }

        int result = maxBrackets(digits, operations);
        System.out.println(result);

    }

    private static int maxBrackets(int[] digits, List<BiFunction<Integer, Integer, Integer>> ops) {
        int[][] D = new int[digits.length + 1][digits.length + 1];
        // D[i, j] = ∞:
        for (int[] arr : D) {
            Arrays.fill(arr, Integer.MIN_VALUE);
        }
        int n = digits.length;
        for (int i = 1; i <= n; i++) {

            D[i][i] = digits[i - 1];
        }
        for (int i = 0; i <= 0; i++) {
            for (int j = 1; j <= n; j++) {
////            System.out.printf("i = %s\n, D[0]length is %s, ", i, D[0].length);
                D[i][j] = digits[j - 1];
            }
        }
        D[0][0] = 0;
//        System.out.println(Arrays.toString(digits));
//        System.out.println(ops);

        int a = maxBracketsR(digits, ops, 1, n, D);
        for (int i = 0; i <= n; i++) {
            System.out.println(Arrays.toString(D[i]));
        }
        return a;

    }

    private static int maxBracketsR(int[] digits, List<BiFunction<Integer, Integer, Integer>> ops, int start, int finish, int[][] D) {
        System.out.printf("IN start %s and finish %s \n", start, finish);
        for (int i = 0; i <= digits.length; i++) {
            System.out.println(Arrays.toString(D[i]));
        }

        if (D[start][finish] != Integer.MIN_VALUE) {
            System.out.printf("Return cache value %s for start %s and finish %s \n ", D[start][finish], start, finish);
            return D[start][finish];
        }


//        if (start == finish) {
//            return 0;
//        }

        if (start == finish) {
            return D[start][start];
        }

//        if (start == finish - 1) {
//            D[start][finish] = ops.get(start - 1 ).apply(start, finish);
//            return D[start][finish];
//        }


        for (int i = start; i < finish; i++) {
            int l = maxBracketsR(digits, ops, start, i, D);
            int r = maxBracketsR(digits, ops, i + 1, finish, D);
            System.out.printf("start %s, finish %s, i = %s, l = %s, r = %s, result is %s\n", start, finish, i, l, r, ops.get(i - 1).apply(l, r));
            D[start][finish] = Math.max(D[start][finish], ops.get(i - 1).apply(l, r));
        }

        System.out.printf("calculated value %s \n", D[start][finish]);

        return D[start][finish];
    }
}
