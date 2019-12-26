package com.github.bael;

import java.util.Arrays;
import java.util.Comparator;

public class ChangeDynamic {
    public static void main(String[] args) {
        int sum = 34;
//        Integer[] nominal = new Integer[] {25, 10, 5, 1};
        Integer[] nominal = new Integer[]{12, 7, 1};
//        System.out.println(Arrays.toString(change(sum, nominal)));

        System.out.println(change(sum, nominal)[sum]);

//        System.out.println(74 / 12);
    }

    private static int[] change(int sum, Integer[] nominal) {
        Arrays.sort(nominal);
        int[][] attempts = new int[nominal.length][sum+1];
        int[][] comb = new int[sum+1][nominal.length];

        for (int i = 1; i <= sum; i++) { attempts[0][i] = i; //            map.put(i, Collections.singleton(i));
            comb[i][0] = i;
        }

        for (int k = 1; k < nominal.length; k++) {
//            Arrays.fill(attempts[k], Integer.MAX_VALUE);
            int coinValue = nominal[k];
//            attempts[k][0] = 0;

            for (int i = 1; i <= sum; i++) {
                attempts[k][i] = Integer.MAX_VALUE;
                for (int j = 1; j * coinValue <= i; j++) {
//                    if (j > 24 && k == 1) {
//                        System.out.println("");
//                    }
                    //int cnt = j / coinValue;
                    // индекс остатка для номиналов поменьше
                    int remainder = i - (j * coinValue);

                    int tmpCount = j + attempts[k - 1][remainder];
                    if (attempts[k][i] > tmpCount) {
                        attempts[k][i] = tmpCount;
                        comb[i][k] = j;
                        // копируем путь
                        for (int l=k-1; l>=0; l--) {
                            comb[i][l] = comb[remainder][l];
                        }
                        if (i==sum && k == 2) {
                            System.out.println("remainder is " + remainder + " and tail is  " + comb[remainder][1] +
                                    " -> " + comb[remainder][0]);


                        }
                    }
//                    attempts[k][i] = Math.min(attempts[k][i],
//                            cnt + attempts[k - 1][remainder]);

//                    System.out.println("cnt is ");
//                    System.out.println(j / coinValue);
//                    System.out.printf("i is " + i+" j is "+j+" nominal is "+coinValue+" "+ "cnt is "
//                            + j+ " remainder is " + remainder + " result is "
//                    +       (j + attempts[k - 1][remainder]) + "\n");
                }
            }
        }

        System.out.println("PRINT CONTENT");
        for (int k = 0; k < nominal.length; k++) {
            System.out.println(Arrays.toString(attempts[k]));
      }
        for (int k = 0; k <= sum; k++) {
            System.out.println(Arrays.toString(comb[k]));
        }

        System.out.println("PRINTED CONTENT");
        return attempts[nominal.length-1];
    }
}
