package com.github.bael;


import java.util.Arrays;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.stream.LongStream;

// Расстояние редактирования
public class EditDistance {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String firstLine = sc.nextLine();
        String secondLine = sc.nextLine();
//        String firstLine = "editing";
//        String secondLine = "distance";
        EditDistance seq = new EditDistance();
        System.out.println(seq.editDistance(firstLine, secondLine));
    }

    private int editDistance(String firstLine, String secondLine) {
        char[] line1Arr = firstLine.toCharArray();
        char[] line2Arr = secondLine.toCharArray();
        int n = line1Arr.length;
        int m = line2Arr.length;

        int[][] distanceArray = new int[n + 1][m + 1];
        for (int i = 0; i <= n; i++) {
            distanceArray[i][0] = i;
        }
        for (int j = 0; j <= m; j++) {
            distanceArray[0][j] = j;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                int c = diff(line1Arr[i-1], line2Arr[j-1]);
                int min = Math.min(distanceArray[i - 1][j] + 1, distanceArray[i][j - 1] + 1);
                distanceArray[i][j] = Math.min(min, distanceArray[i-1][j -1] + c);
            }
        }
//        for (int i=0; i<n+1; i++) {
//            System.out.println(Arrays.toString(distanceArray[i]));
//        }

        return distanceArray[n][m];
    }

    private int diff(char c1, char c2) {
        if (c1 == c2) {
            return 0;
        };
        return 1;
    }


}
