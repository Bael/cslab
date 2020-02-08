package com.github.bael.csprogram;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class StackWithMaxDemo {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int count = Integer.parseInt(sc.nextLine());

        StringBuilder builder = new StringBuilder();
        StackWithMax max = new StackWithMax();
        for (int i = 0; i < count; i++) {
            String command = sc.nextLine();
            if (command.startsWith("max")) {
                int maxVal = max.max();
                if (Integer.MIN_VALUE != maxVal) {
                    builder.append(maxVal);
                    builder.append(System.lineSeparator());
                }
            } else if (command.startsWith("push")) {
                int val = Integer.parseInt(command.substring("push ".length()));
                max.push(val);
            } else if (command.startsWith("pop")) {
                max.pop();
            } else {
                throw new RuntimeException("Unknown command!" + command);
            }
        }
        System.out.println(builder.toString());
    }

//    private static class HardStack {
//        private int[] stackArray;
//        private int head = 0;
//        private int head() {
//            return head;
//        }

//        public HardStack(int capacity) {
//            stackArray = new int[capacity + 1];
//            stackArray[0] = Integer.MIN_VALUE;
//        }
//
//        public boolean isEmpty() {
//            return head == 0;
//        }
//        public int pop() {
//            if (!isEmpty()) {
//                head--;
//                return stackArray[head() + 1];
//            } else {
//                return 0;
//            }
//        }
//
//        public int last() {
//            return stackArray[head()];
//        }
//
//        public int max() {
//            return last();
//        }
//
//        public void push(int val) {
//            stackArray[head() +1] = Math.max(val, last());
//            head++;
//        }
//    }

    private static class StackWithMax {
        private Deque<Integer> maxStack = new ArrayDeque<>();

        public Integer max() {
            if (maxStack.isEmpty()) {//    private static class HardStack {
//        private int[] stackArray;
//        private int head = 0;
//        private int head() {
//            return head;
//        }

                return Integer.MIN_VALUE;
            }
            return maxStack.peekFirst();
        }

        public Integer pop() {
            if (!maxStack.isEmpty()) {
                maxStack.pop();
                return 0;
            }
            return 0;
        }

        public void push(Integer value) {
            int max = Math.max(value, max());
            maxStack.push(max);
        }
    }

}
