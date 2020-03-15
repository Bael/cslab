package com.github.bael.csprogram;

import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Хэш таблица на массиве
 */
public class TextSearchDemo {
    public static void main(String[] args) throws FileNotFoundException {

        String fileName = "src/main/resources/text_search.txt";
//        Scanner sc = new Scanner(new File(fileName));
        Scanner sc = new Scanner(System.in);
//        System.out.println(chain.getHashCode("HellO"));
//        System.out.println(chain.getHashCode("world"));
//        System.out.println(chain.getHashCode("worldworldworla"));

        new TextSearchDemo().runDemo(sc);
//        System.out.println();
    }


    private int getHashCode(byte[] ascii, int p, int x) {
        long xi = 1;
        long sum = 0;
        for (int i = 0; i < ascii.length; i++) {
            if (i > 0) {
                xi = mod(xi * x, p);
            }
            sum += (ascii[i] * xi);
//            if (i== ascii.length - 1) {
//                System.out.println("|P| hash  is " + ascii[i] * xi + " for xP " + xi + " and token " + ascii[i]);
//            }
        }
        long result = mod(sum, p);
        return (int) result;
    }

    private int getHashCode(byte[] ascii, int start, int length, int p, int x) {
        long xi = 1;
        long sum = 0;
        for (int i = 0; i < length; i++) {
            if (i > 0) {
                xi = mod(xi * x, p);
            }
            sum += (ascii[i + start] * xi);
        }
        long result = mod(sum, p);
        return (int) result;
    }

    private int getHashCode(String string, int p, int x) {
        byte[] ascii = string.getBytes(StandardCharsets.US_ASCII);
        return getHashCode(ascii, p, x);
    }

    private void runDemo(Scanner sc) {
        // ключ поиска
        String search = sc.nextLine();
        byte[] searchArray = search.getBytes(StandardCharsets.US_ASCII);

        // текст для поиска
        String text = sc.nextLine();
        byte[] textArray = text.getBytes(StandardCharsets.US_ASCII);

        Random r = new Random();
        int p = 4999;
        int x = 31; //Math.max(1, r.nextInt(p));
        int pLength = searchArray.length;
        // x^|P|
        long xP = 1;
        for (int i = 1; i < pLength; i++) {
            xP = mod(xP * x, p);
        }

        List<Integer> ids = new ArrayList<>();
        long searchHash = getHashCode(search, p, x);
        long lastHash = getHashCode(textArray, textArray.length - searchArray.length,
                searchArray.length, p, x);
        if (mod(lastHash, p) == mod(searchHash, p)) {
            if (compareArrays(textArray, textArray.length - searchArray.length, searchArray)) {
                ids.add(textArray.length - searchArray.length);
            }
        }
        for (int i = textArray.length - searchArray.length - 1; i >= 0; i--) {

//            System.out.println("|P~| hash  is " + textArray[i + pLength] * xP
//                    + " for xP" + xP+ " and token " + textArray[i + pLength]);

            long mod = mod(lastHash - textArray[i + pLength] * xP, p);
            long newHash = mod(x * mod + textArray[i], p);
//            String substring = text.substring(i, i + pLength);

//            long newHash = getHashCode(substring, p , x);
//            System.out.printf("last hash %s, newhash %s\n", lastHash, newHash);
            // проверка на случай коллизий
            if (newHash == searchHash) {
                if (compareArrays(textArray, i, searchArray)) {
                    ids.add(i);
                }
            }
            lastHash = newHash;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = ids.size() - 1; i >= 0; i--) {
            sb.append(ids.get(i));
            sb.append(" ");
        }
        System.out.println(sb.toString());
//        Collections.reverse(ids);
//        ids.forEach(integer -> System.out.printf("%s ", integer));
//        System.out.println();
    }

    private long mod(long a, long p) {
        return ((a % p) + p) % p;
    }

    private boolean compareArrays(byte[] bigArray, int start, byte[] searchArray) {
        for (int i = start; i < start + searchArray.length; i++) {
            if (bigArray[i] != searchArray[i - start]) {
                return false;
            }
        }
        return true;
    }


}
