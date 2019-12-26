package com.github.bael;

import java.util.Scanner;
import java.util.stream.Stream;

public class BracketsDynProgramming {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        char[] chars = line.toCharArray();
        // operations = digits - 1; line.length = operations + digits => digits = (line.length + 1) / 2
        int digitsCount = (chars.length + 1) / 2;
        int operationsCount = digitsCount - 1;

        char[] operations = new char[operationsCount];
        char[] digitscount = new char[operationsCount];



    }


}
