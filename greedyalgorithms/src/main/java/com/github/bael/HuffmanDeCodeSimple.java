package com.github.bael;

import java.util.*;

// наивная реализация
public class HuffmanDeCodeSimple {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Integer count = sc.nextInt();
        sc.nextLine();

        HashMap<String, String> map = new HashMap<>();
        for (int i=0; i<count; i++) {
            String codeString = sc.nextLine();
            String[] arr = codeString.split(": ");
            String letter = arr[0];
            String code = arr[1];
            map.put(code, letter);
        }

        String source = sc.nextLine();
        StringBuilder sb = new StringBuilder();
        StringBuilder answer = new StringBuilder();
        for (String s : source.split("")) {
            sb.append(s);
            if (map.containsKey(sb.toString())) {
                answer.append(map.get(sb.toString()));
                sb.delete(0, sb.length());
            }
        }
        System.out.println(answer.toString());
    }


}
