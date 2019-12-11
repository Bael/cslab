package com.github.bael;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class RadixSort {

    public static void main(String[] args) {
//        int[] run = run(new Scanner(System.in), 11);
//        IntStream.of(run).forEach(value -> System.out.printf("%s "));


//        System.out.println(Math.log());
    }

    private static int[] run(Scanner sc, int limit) {
        // read data
        Objects.requireNonNull(sc, "Не заполнен сканер чтения данных!");
        // инициализируем массив координат
        int count = sc.nextInt();
        int[] source = new int[count];

        for (int i = 0; i < count; i++) {
            source[i] = sc.nextInt();
        }
        return radixSort(source, limit);
    }

    private static int[] radixSort(int[] source, int limit) {
        // {массивAсодержит целые числа от1доM}создать массив
        // // B[1. . .M]←[0,0, . . . ,0]
        // для
        // jот 1 до n: B[A[j]]←B[A[j]] +1
        // для
        // iот2доM:B[i]←B[i] +B[i−1]
        // для
        // jотnдо1:A′[B[A[j]]]←A[j]B[A[j]]←B[A[j]]−1
        int[] radixArray = new int[limit];
        int[] sortedArray = new int[source.length];
        for (int i=0; i<source.length; i++) {
            radixArray[source[i]]++;
        }
//        System.out.println(Arrays.toString(radixArray));

        for (int i=1; i<limit; i++) {
            radixArray[i] += radixArray[i - 1];
        }

//        System.out.println(Arrays.toString(radixArray));

        for (int i=source.length-1; i>=0; i--) {
            sortedArray[radixArray[source[i]]-1] = source[i];
            radixArray[source[i]]--;
        }
        return sortedArray;
    }
}
