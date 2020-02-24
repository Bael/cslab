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
