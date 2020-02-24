package com.github.bael.csprogram;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class PriorityQueueDemo {

    public static void main(String[] args) throws FileNotFoundException {

        String fileName = "src/main/resources/pq_input.txt";
//        Scanner sc = new Scanner(new File(fileName));
        Scanner sc = new Scanner(System.in);
        new PriorityQueueDemo().runDemo(sc);
    }

    private void runDemo(Scanner sc) {
        int arrayLength = Integer.parseInt(sc.nextLine());
        int[] array = new int[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            array[i] = sc.nextInt();
        }

        ArrayPriorityQueue queue = new ArrayPriorityQueue(array);
        queue.buildHeap();

    }


    private class ArrayPriorityQueue {
        /**
         * Источник данных для кучи
         */
        private int[] source;
        private int size;
        private ArrayList<Integer[]> swapHistory = new ArrayList<>(50000);


        // верх (a) меньше низа (b)?
//        private BiFunction<Integer, Integer, Boolean> invariant = (a, b) -> a <= b;

        public ArrayPriorityQueue(int[] source) {
            this.source = source;
            this.size = source.length;
        }

        private boolean invariant(int a, int b) {
            return a <= b;
        }

        private void siftDown(int index) {
            int maxIndex = index;
            int l = getLeftChildIndex(index);
            // верх больше низа - нужно менять местами
            if (l < size && !invariant(source[maxIndex], source[l])) {
                maxIndex = l;
            }
            int r = getRightChildIndex(index);
            // правый потомок меньше низа\ левого потомка
            if (r < size && !invariant(source[maxIndex], source[r])) {
                maxIndex = r;
            }

            // нужна замена?
            if (index != maxIndex) {
                swap(index, maxIndex);
                Integer[] a = new Integer[2];
                a[0] = index;
                a[1] = maxIndex;
                swapHistory.add(a);

                siftDown(maxIndex);
            }
        }

        private void swap(int l, int r) {
            if (l < size && r < size) {
                int tmp = source[l];
                source[l] = source[r];
                source[r] = tmp;
            } else {
                throw new RuntimeException("Wrong indices to swap " + l + " " + r);
            }
        }

        private void siftUp(int index) {
            while (index > 0 && invariant(source[getParentIndex(index)], source[index])) {
                swap(getParentIndex(index), index);
                index = getParentIndex(index);
            }
        }

        private int getLeftChildIndex(int index) {
            return 2 * index + 1;
        }

        private int getRightChildIndex(int index) {
            return 2 * index + 2;
        }

        private int getParentIndex(int index) {
            return (index - 1) / 2;
        }

        private int getElement(int index) {
            if (index < size) {
                return source[index];
            }
            return Integer.MAX_VALUE;
        }

        /**
         * Превращаем массив в кучу
         */
        public void buildHeap() {
            for (int i = size / 2; i >= 0; i--) {
                siftDown(i);
            }

            System.out.println(swapHistory.size());
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < swapHistory.size(); i++) {
                sb.append(swapHistory.get(i)[0]);
                sb.append(" ");
                sb.append(swapHistory.get(i)[1]);
                sb.append(System.lineSeparator());

//                System.out.printf("%s %s\n", swapHistory.get(i)[0], swapHistory.get(i)[1]);
            }
            System.out.println(sb.toString());
        }
    }
}
