package com.github.bael;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Scanner;

public class BinarySearch {
    public static void main(String[] args) {
//        new BinarySearch().run();
        Integer flag = 256;
        System.out.println(Integer.toBinaryString(flag));
        int count = 0;

        for (int i=0; i< 256; i++) {
            for (int j=0; j < 256; j++) {
                if (((i + j) & flag) != 0) {
                    count++;
                    System.out.println(Integer.toBinaryString(i));
                    System.out.println(Integer.toBinaryString(j));
                    System.out.println();

                }
            }
        }
        System.out.printf("count is %s from total %s", count, 256*256);
    }

    private void run() {
        Scanner sc = new Scanner(System.in);
        int[] sourceArray = readArray(sc);
        Arrays.sort(sourceArray);
        int[] numbersToFind = readArray(sc);
        findInArray(sourceArray, numbersToFind);
    }

    private void testRun() {
        findInArray(new int[] {1, 2, 3 ,4, 5 ,6 ,7 ,8 }, new int[] {1, 2, 3 ,7, 8});
    }

    private void findInArray(int[] sourceArray, int[] numbersToFind) {
        for(int numberToFind : numbersToFind) {
            System.out.printf("%s ", binarySearch(sourceArray, numberToFind));
        }

    }

    private int binarySearch(int[] source, int numberToFind) {
        int l = 0;
        int r = source.length - 1;
        while (l<=r) {
            int m = (l + r) / 2;
            if (source[m] == numberToFind) {
                return m + 1;
            }
            if (source[m] < numberToFind) {
                l = m + 1;
            } else {
                r = m - 1;
            }

        }
        return -1;

    }

    private int[] readArray(Scanner sc) {
        int arraySize = sc.nextInt();
        int[] numbers = new int[arraySize];
        for(int i=0; i<arraySize; i++) {
            numbers[i] = sc.nextInt();
        }
        return numbers;
    }
}
