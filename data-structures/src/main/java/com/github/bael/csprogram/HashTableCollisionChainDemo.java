package com.github.bael.csprogram;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Scanner;

/**
 * Хэш таблица на массиве
 */
public class HashTableCollisionChainDemo {
    public static void main(String[] args) throws FileNotFoundException {

        String fileName = "src/main/resources/hash_table_collision_chain.txt";
        Scanner sc = new Scanner(new File(fileName));
//        Scanner sc = new Scanner(System.in);
        HashTableWithChain chain = new HashTableWithChain(5);
//        System.out.println(chain.getHashCode("HellO"));
//        System.out.println(chain.getHashCode("world"));
//        System.out.println(chain.getHashCode("worldworldworla"));

        new HashTableCollisionChainDemo().runDemo(sc);
//        System.out.println();
    }

    private void runDemo(Scanner sc) {

        int m = sc.nextInt();
        int n = sc.nextInt();


        HashTableWithChain book = new HashTableWithChain(m);
        for (int i = 0; i < n; i++) {
            String command = sc.next();

            switch (command) {
                case "add":
                    book.add(sc.next());
                    break;
                case "del":
                    book.del(sc.next());
                    break;
                case "find":
                    System.out.println(book.find(sc.next()));
                    break;
                case "check":
                    System.out.println(book.check(sc.nextInt()));
                    break;
            }
        }
    }

    private interface SymbolTable {
        void add(String string);

        String find(String string);

        void del(String string);

        String check(int index);
    }

    private static class HashTableWithChain implements SymbolTable {
        ArrayList<LinkedList<String>> table;
        int capacity;

        public HashTableWithChain(int capacity) {
            table = new ArrayList<>(capacity + 1);
            for (int i = 0; i < capacity; i++) {
                table.add(null);
            }
            this.capacity = capacity;
        }

        private int getHashCode(String string) {
            byte[] ascii = string.getBytes(StandardCharsets.US_ASCII);
//            System.out.println(ascii);

            long xi = 1;
            long sum = 0;
            final long p = 1_000_000_007L;
            for (int i = 0; i < string.length(); i++) {
                if (i > 0) {
                    xi = (xi * 263) % p;
                }
                sum += (ascii[i] * xi);
            }
            long result = ((sum) % p) % capacity;
            return (int) result;

        }

        @Override
        public void add(String string) {
            if (find(string).equals("yes")) {
                return;
            }
            int hash = getHashCode(string);
            LinkedList<String> list = table.get(hash);
            if (list == null) {
                list = new LinkedList<>();
                table.set(hash, list);
            }
            list.add(0, string);
        }

        @Override
        public String find(String string) {
            int hash = getHashCode(string);
            LinkedList<String> list = table.get(hash);
            if (list != null) {
                for (String item : list) {
                    if (item.equals(string)) {
                        return "yes";
                    }
                }
            }
            return "no";
        }

        @Override
        public void del(String string) {
            int hash = getHashCode(string);
            LinkedList<String> list = table.get(hash);
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    String item = list.get(i);
                    if (item.equals(string)) {
                        list.remove(i);

                        if (list.isEmpty()) {
                            table.set(hash, null);
                        }
                        return;
                    }
                }
            }
        }

        Optional<LinkedList<String>> findChain(String s) {
            int hash = getHashCode(s);
            LinkedList<String> list = table.get(hash);
            return Optional.ofNullable(list);
        }

        @Override
        public String check(int index) {
            LinkedList<String> list = table.get(index);
            if (list == null) {
                return "";
            }

//            System.out.println(list);
            StringBuilder sb = new StringBuilder();
            list.forEach(s -> {
                sb.append(s);
                sb.append(" ");
            });
            return sb.toString();
        }
    }


}
