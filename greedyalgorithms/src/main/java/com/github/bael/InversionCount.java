package com.github.bael;


import java.util.Scanner;

public class InversionCount {
    long inversionCount = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int count = sc.nextInt();

        long[] numbers = new long[count];

        for (int i = 0; i < count; i++) {
            numbers[i] = sc.nextLong();
        }
        InversionCount a = new InversionCount();
        a.mergeSort(numbers, 0, numbers.length - 1);
        System.out.println(a.inversionCount);
    }

    private long[] mergeSort(long[] numbers, int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;
            return merge(mergeSort(numbers, l, m), mergeSort(numbers, m + 1, r));
        }
        return new long[]{numbers[l]};
    }


    private long[] merge(long[] left, long[] right) {
        long[] result = new long[left.length + right.length];
        int leftIndex = 0;
        int rightIndex = 0;

        for (int i = 0; i < result.length; i++) {
            if ((rightIndex >= right.length)
                    || ((leftIndex < left.length)
                    && (left[leftIndex] <=
                    right[rightIndex]))) {
                result[i] = left[leftIndex];
                leftIndex++;
            } else {
                // нарушение сортировки
                result[i] = right[rightIndex];
                if (leftIndex < left.length) {
                    inversionCount += left.length - leftIndex;
                }
                rightIndex++;
            }

        }
        return result;
    }


}
