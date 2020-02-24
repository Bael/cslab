package com.github.bael.csprogram;

import java.util.Random;
import java.util.Scanner;

public class GuessNumber {
    public static void runGuess() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Загадай число от 1 до 100");
        System.out.println("Я буду писать тебе числа, отвечай твое число больше, меньше или равно тому что я написал.");
        int low = 1;
        int hi = 100;
        while (true) {
            int mid = (hi + low) / 2;
            System.out.println("Это число " + mid + "? больше/меньше/равно.");
            while (true) {
                String answer = sc.next().toLowerCase();
                if (answer.startsWith("больше")) {
                    low = mid;
                    break;
                } else if (answer.startsWith("меньше")) {
                    hi = mid;
                    break;
                } else if (answer.startsWith("равно")) {
                    System.out.println("Угадал!!!!");
                    return;
                } else {
                    System.out.println("Не распознал команду. Попробуй еще раз.");
                }
            }
        }


    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int bound = 10000;
        System.out.println("Привет! Давай играть в загадай числа! Я загадываю число от 1 до " + bound + ", а ты угадываешь!");

        Random a = new Random();
        int answer = a.nextInt(bound);
        int guess = -1;
        while (guess != answer) {

            guess = sc.nextInt();
            if (guess < answer) {
                System.out.println("Больше!");
            } else if (guess > answer) {
                System.out.println("Меньше!");
            } else {
                System.out.println("Угадал!!!");
            }
        }
    }
}
