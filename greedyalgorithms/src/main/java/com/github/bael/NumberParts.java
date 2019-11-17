package com.github.bael;

import java.util.*;

public class NumberParts {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

//        for (int i =0; i< 15; i++) {
//            System.out.print("sum for i " + i + " = " +max(i).stream().mapToInt(value -> value).sum());
//            System.out.print(" with array");
//            System.out.println(max(i));
//
//        }
        List<Integer> list = max(n);
        System.out.println(list.size());
        list.forEach(integer -> System.out.printf("%d ", integer));
    }

    private static List<Integer> max(int n) {
        int current = 0;
        int remain = n ;
        List<Integer> list = new ArrayList<>();

        while (remain > 0) {
            current++;
            if (current <= remain) {
                list.add(current);
                remain -= current;
            } else {
                list.add(remain + list.remove(list.size() - 1));
                remain = 0;
            }
        }
        return list;
    }


}
