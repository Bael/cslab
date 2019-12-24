package com.github.bael;


import java.util.Arrays;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.stream.LongStream;

// Наибольшая возрастающая последовательность
public class LongestIncSequenceEx {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int count = sc.nextInt();
        long[] numbers = new long[count];
        for (int i = 0; i < count; i++) {
            numbers[i] = sc.nextLong();
        }

        LongestIncSequenceEx seq = new LongestIncSequenceEx();
//        BiFunction<Long, Long, Boolean> cmpFunction = (aLongLeft, aLongRight) -> aLongLeft >= aLongRight && (aLongRight % aLongLeft == 0);
        for (int i=0; i< numbers.length / 2; i++) {
            long tmp = numbers[i];
            numbers[i] = numbers[numbers.length - i - 1];
            numbers[numbers.length - i - 1] = tmp;
        }
        int[] result = seq.find(numbers);
        System.out.println(result.length);
        for (int i=result.length-1; i>=0; i--) {
            System.out.printf("%s ", numbers.length - result[i]);
        }
    }

    private int[] find(long[] numbers) {
        long[] countSeq = new long[numbers.length];
        // initialization
        Arrays.fill(countSeq, 1);
        for (int i = 0; i < numbers.length; i++) {
            countSeq[i] = 1;
            for (int j = 0; j < i; j++) {
                if (countSeq[j] + 1 > countSeq[i] && (numbers[j] <= numbers[i])) {
                    countSeq[i] = countSeq[j] + 1;
                }
            }
        }
        return restoreSeq(numbers, countSeq);
    }

    private int[] restoreSeq(long[] numbers, long[] countSeq) {
        int count = (int) LongStream.of(countSeq).max().orElse(0);
        int[] seqArray = new int[count];
        int currentSeqCount = count;
        for (int i = countSeq.length - 1; i >= 0; i--) {
            if (countSeq[i] == currentSeqCount) {
                if (currentSeqCount == count ||
                        (numbers[i] <= numbers[seqArray[currentSeqCount]])) {
                    seqArray[--currentSeqCount] = i;
                }
            }
        }
        return seqArray;
    }


}
