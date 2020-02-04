package com.github.bael.csprogram;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class BracketsCheck {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        System.out.println(getBracketCheckResult(input));
//
//        System.out.println(getBracketCheckResult("([](){([])})"));
//        System.out.println(getBracketCheckResult("()[]}"));
//        System.out.println(getBracketCheckResult("{{[()]]"));
//        System.out.println(getBracketCheckResult("if (i < 0) { "));
    }

    private static String getBracketCheckResult(String input) {
        int i = errorIndex(input);
        if (i < 0) {
            return "SUCCESS";
        } else {
            return String.valueOf(i + 1);
        }
    }

    /**
     * Возвращает индекс ошибки в тексте со скобками в строке.
     * Ошибкой считается индекс символа в котором для закрывающей скобки не найдено парной открывающей
     * Либо, после обработки строки индекс последней открывающей скобки, для которой не нашлой парной закрывающей
     *
     * @param input входная последовательность символов
     * @return индекс ошибки, если найдена. -1, если ошибок нет
     */
    private static int errorIndex(String input) {
        char[] chars = input.toCharArray();
        Deque<Character> characters = new ArrayDeque<>();
        Deque<Integer> indexes = new ArrayDeque<>();

        for (int i = 0; i < chars.length; i++) {
            Character currentSymbol = chars[i];
            if (isOpenBracket(currentSymbol)) {
                characters.push(currentSymbol);
                indexes.push(i);
            } else if (isClosedBracket(currentSymbol)) {
                // empty stack case
                if (characters.isEmpty()) {
                    return i;
                }
                Character openBracket = characters.pop();
                // [  ']'
                if (matched(openBracket, currentSymbol)) {
                    indexes.pop();
                } else {
                    // [  '}'
                    return i;
                }
            }

        }
        if (!indexes.isEmpty()) {
            return indexes.pop();
        }
        // success
        return -1;
    }

    private static boolean matched(Character openBracket, Character closedBracket) {
        return -getBracketCode(openBracket) == getBracketCode(closedBracket);
    }

    private static boolean isClosedBracket(Character c) {
        return getBracketCode(c) > 0;
    }

    /**
     * Код для поданного символа
     *
     * @param c - символ который хотим проверить
     * @return 0 - если это не скобка, иначе - n если это открывающая скобка и +n если это закрывающая скобка
     */
    private static int getBracketCode(Character c) {
        switch (c) {
            case '[':
                return -1;
            case ']':
                return 1;
            case '{':
                return -2;
            case '}':
                return 2;
            case '(':
                return -3;
            case ')':
                return 3;
            default:
                return 0;
        }
    }

    private static boolean isOpenBracket(Character c) {
        return getBracketCode(c) < 0;
    }

}
