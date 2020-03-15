package com.github.bael.csprogram;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Хэш таблица на массиве
 */
public class HashTableDirectAccessDemo {
    public static void main(String[] args) throws FileNotFoundException {

//        String fileName = "src/main/resources/hash_table_phone_input.txt";
//        Scanner sc = new Scanner(new File(fileName));
        Scanner sc = new Scanner(System.in);
        new HashTableDirectAccessDemo().runDemo(sc);
    }

    private void runDemo(Scanner sc) {

        int count = sc.nextInt();
        int capacity = 1000_000_0;
        PhoneBook book = new PhoneBook(capacity);
        for (int i = 0; i < count; i++) {
            String command = sc.next();
            int number = Integer.parseInt(sc.next());
            switch (command) {
                case "add":
                    book.add(number, sc.next());
                    break;
                case "del":
                    book.del(number);
                    break;
                case "find":
                    System.out.println(book.find(number));
                    break;
            }
        }
    }

    private interface SymbolTable {
        void add(int number, String name);

        String find(int number);

        void del(int number);
    }

    private static class PhoneBook implements SymbolTable {
        String[] table;

        public PhoneBook(int capacity) {
            table = new String[capacity];
        }

        @Override
        public void add(int number, String name) {
            table[number] = name;
        }

        @Override
        public String find(int number) {
            if (table[number] != null) {
                return table[number];
            }
            return "not found";
        }

        @Override
        public void del(int number) {
            table[number] = null;
        }
    }


}
