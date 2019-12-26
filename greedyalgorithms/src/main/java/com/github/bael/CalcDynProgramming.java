package com.github.bael;

import java.util.*;

public class CalcDynProgramming {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int number = sc.nextInt();
//        int number = 96234;
//        int number = 96;

        CalcDynProgramming a = new CalcDynProgramming();
//        System.out.println(min(1, -5, 3));

        List<Integer> arr = a.minCountChain2(number);
        System.out.println(arr.size() - 1);
        arr.forEach(integer -> System.out.printf("%s ", integer));
        System.out.println();

//        int x = 1;
//        System.out.println(arr.size());
//        System.out.printf("%s ", x);
//        for (int i=0; i< arr.size(); i++) {
//            switch (arr.get(i)) {
//                case 1: x = x + 1; break;
//                case 2: x = x * 2; break;
//                case 3: x = x * 3; break;
//            }
//            System.out.printf("%s ", x);
//        }
//        System.out.println();

    }

    private List<Integer> minCountChain2(int number) {

        int[] steps = new int[number + 1];

        for (int i=2; i<=number; i++) {
            // x => x + 1
            int minimum = steps[i - 1] + 1;

            // x => x * 2
            if (i % 2 == 0) {
                minimum = Math.min(minimum, 1 + steps[i / 2]);
            }

            // x => x * 3
            if (i % 3 == 0) {
                minimum = Math.min(minimum, 1 + steps[i / 3]);
            }
            steps[i] = minimum;
        }

//        System.out.println(Arrays.toString(steps));

        List<Integer> a = new ArrayList<>();
        // восстанавливаем в обратном порядке

        int i = number;
        a.add(0, number);
        while (i>1) {
            // шаг назад

            if (steps[i] == (1 + steps[i-1])) {
                i = i - 1;
                a.add(0, i);

                continue;
            }

            if (i % 2 == 0 && (steps[i] == 1 + steps[i / 2])) {
                i = i / 2;
                a.add(0, i);
                continue;
            }
            if (i % 3 == 0 && steps[i] ==  1 + steps[i / 3]) {
                i = i / 3;
                a.add(0, i);
                continue;
            }
        }
        return a;
    }

    private List<Byte> minCountChain(int number, Map<Integer, List<Byte>> cache) {
        if (number == 1) {
            return new ArrayList<>();
        }

        if (cache.containsKey(number)) {
            return new ArrayList<>(cache.get(number));
        }
        // fill chains
        List<Byte> F3 = null;
        if (number / 3 >= 1 && number % 3 == 0) {
            F3 = minCountChain(number / 3, cache);
            F3.add((byte) 3);
        }

        List<Byte> F2 = null;
        if (number / 2 >= 1 && number % 2 == 0) {
            F2 = minCountChain(number / 2, cache);
            F2.add((byte) 2);
        }

        List<Byte> F1 = minCountChain(number - 1, cache);
        F1.add((byte) 1);

        // вычисляем минимум
        List<Byte> min = F1;
        if (F2 != null) {
            if (F2.size() < min.size()) {
                min = F2;
            }
        }
        if (F3 != null) {
            if (F3.size() < min.size()) {
                min = F3;
            }
        }

        cache.put(number, min);
        // F[N] = min(F(N-1), F(N/2), F(N/3))
        return min;
    }

}
