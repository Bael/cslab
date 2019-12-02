package com.github.bael;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

public class ArrayBinaryHeapEx2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int count = sc.nextInt();
        sc.nextLine();
        ArrayBinaryHeap heap = new ArrayBinaryHeap();

        for (int i=0; i<count; i++) {
            String codeString = sc.nextLine();
            if (codeString.contains("ExtractMax")) {
                System.out.println(heap.extractMax());
            } else {
                long val = Long.parseLong(codeString.split(" ")[1]);
                heap.insert(val);
            }
        }

    }

    private static class ArrayBinaryHeap {
        ArrayList<Long> heap = new ArrayList<>();

        // i = 1..n
        private long getValue(int i) {
            return heap.get(i);
        }

        private int getSize() {
            return heap.size() - 1;
        }


        private int getLeftChildIndex(int j) {
            return Math.min(getSize(), j * 2 + 1);
        }

        private int getRightChildIndex(int j) {
            return Math.min(getSize(), j * 2 + 2);
        }

        private int getParentIndex(int j) {
            return (j - 1) / 2;
        }

        private boolean invariant(int parent, int child) {
            return getValue(parent) > getValue(child);
        }

        private void siftUp(int i) {
            while (i>0 && !invariant(getParentIndex(i), i)) {
                swap(getParentIndex(i), i);
                i = getParentIndex(i);
            }
        }

        private void swap(int i, int j) {
            long a = getValue(i);
            heap.set(i, heap.get(j));
            heap.set(j, a);
        }

        public void insert(long value) {
            heap.add(value);
            siftUp(getSize());
        }



        public void sinkDown(int i)
        {
            int leftChild;
            int rightChild;
            int largestChild;

            for (; ; )
            {
                leftChild = 2 * i + 1;
                rightChild = 2 * i + 2;
                largestChild = i;

                if (leftChild < heap.size() && heap.get(leftChild) > heap.get(largestChild))
                {
                    largestChild = leftChild;
                }

                if (rightChild < heap.size() && heap.get(rightChild) > heap.get(largestChild))
                {
                    largestChild = rightChild;
                }

                if (largestChild == i)
                {
                    break;
                }
                swap(i, largestChild);
                i = largestChild;

            }
        }

        public long extractMax() {
            swap(0, getSize());
            long max = heap.remove(getSize());
            sinkDown(0);
            return max;
        }

    }


}

