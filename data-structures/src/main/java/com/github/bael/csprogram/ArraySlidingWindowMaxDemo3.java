package com.github.bael.csprogram;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;
import java.util.stream.IntStream;

public class ArraySlidingWindowMaxDemo3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int arrayLength = Integer.parseInt(sc.nextLine());
        int[] array = new int[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            array[i] = sc.nextInt();
        }
        int slideLength = sc.nextInt();
        StringBuilder sb = new StringBuilder();
        IntStream.of(new SlidingMax(array, slideLength)
                .calculate()).forEach(value -> {
            sb.append(value);
            sb.append(" ");
        });
        System.out.println(sb.toString());

    }

    private static class SlidingMax {
        private int[] array;
        private int slideSize;
        private int[] maxArray;


        public SlidingMax(int[] array, int slideSize) {
            this.array = array;
            this.slideSize = slideSize;
            maxArray = new int[array.length - slideSize + 1];
        }

        public int[] calculate() {
            StackWithMax stackPartOne = new StackWithMax();
            StackWithMax stackPartTwo = new StackWithMax();

            int idx = 0;
            while (idx < array.length) {
                // initial grab
                if (stackPartOne.isEmpty() && stackPartTwo.isEmpty()) {
                    // sout initial grab
                    for (int i = 0; i < slideSize; i++) {
                        stackPartOne.push(array[idx++]);
                    }
                    while (!stackPartOne.isEmpty()) {
                        stackPartTwo.push(stackPartOne.pop());
                    }
                    maxArray[idx - slideSize] = stackPartTwo.max();
                } else {
                    // refill stack two
                    if (stackPartTwo.isEmpty()) {
                        while (!stackPartOne.isEmpty()) {
                            stackPartTwo.push(stackPartOne.pop());
                        }
                    } else {
                        stackPartOne.push(array[idx++]);
                        stackPartTwo.pop();
                        maxArray[idx - slideSize] = Math.max(stackPartTwo.max(), stackPartOne.max());
                    }
                }
            }
            return maxArray;
        }
    }

    private static class StackWithMax {
        private Deque<Integer> maxStack = new ArrayDeque<>();
        private Deque<Integer> values = new ArrayDeque<>();

        public Integer max() {
            if (maxStack.isEmpty()) {
                return Integer.MIN_VALUE;
            }
            return maxStack.peekFirst();
        }

        public Integer pop() {
            if (!maxStack.isEmpty()) {
                maxStack.pop();
                return values.pop();
            }
            throw new RuntimeException("empty stack");
        }

        public void push(Integer value) {
            int max = Math.max(value, max());
            maxStack.push(max);
            values.push(value);
        }

        public boolean isEmpty() {
            return maxStack.isEmpty();
        }
    }
}
