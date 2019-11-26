package com.github.bael;

import java.util.*;

// наивная и медленная реализация
public class HuffmanCodeSimple {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String source = sc.nextLine();
        runHuffmanCodesCalculation(source);
    }

    private static void runHuffmanCodesCalculation(String source) {
        // Подсчет частот
        Map<String, Node> freq = buildFrequencies(source);
        // Храним список листьев
        List<Node> nodes = new ArrayList<>(freq.values());
        // Строим иерархию кодов для букв
        Node rootNode = buildCodes(freq);
        // Строим коды букв для вывода на экран
        Map<String, String> codes = new HashMap<>();
        for (Node node: nodes) {
            codes.put(node.letter, node.getCode());
        }

        // Строим код хаффмана для всей строки
        StringBuilder code = new StringBuilder();
        for (String s: source.split("")) {
            code.append(codes.get(s));
        }

        String answer = code.toString();
        System.out.println(nodes.size()  + " " + answer.length());

        for (String s: codes.keySet()) {
            System.out.println(s + ": " + codes.get(s));
        }
        System.out.println(answer);
    }

    private static Map<String, Node> buildFrequencies(String source) {
        Map<Character, Integer> freq = new HashMap<>();
        for(Character character : source.toCharArray()) {
            Integer oldFreq = freq.getOrDefault(character, 0);
            freq.put(character, oldFreq + 1);
        }

        Map<String, Node> nodes = new HashMap<>();
        for (Character c : freq.keySet()) {
            nodes.put(c.toString(), new Node(freq.get(c), c.toString()));
        }

        return nodes;
    }

    private static Node buildCodes(Map<String, Node> freq) {
        while (freq.size() > 1) {
            Node left = getMinNode(freq);
            Node right = getMinNode(freq);
            Node newNode = new Node(left, right);
            freq.put(newNode.letter, newNode);
        }

        Optional<String> key = freq.keySet().stream().findFirst();
        return key.map(freq::remove).orElse(null);
    }

    private static Node getMinNode(Map<String, Node> freq) {
        int min = Integer.MAX_VALUE;
        String minChar = null;
        for (String c : freq.keySet()) {
            if (freq.get(c).frequency < min) {
                minChar = c;
                min = freq.get(c).frequency;
            }
        }
        return freq.remove(minChar);
    }

    private static class Node {
        private Node left;
        private Node right;

        @Override
        public String toString() {
            return "Node{" +
                    "order='" + order + '\'' +
                    ", frequency=" + frequency +
                    ", letter='" + letter + '\'' +
                    '}';
        }

        private Node parent;
        private String order;
        private Integer frequency;
        // unique id of node
        private String letter;


        public Node(Node left, Node right) {
            this.left = left;
            left.parent = this;
            this.left.order = "0";

            this.right = right;
            right.order = "1";
            this.right.parent = this;
            this.letter = right.letter + left.letter;
            this.order = "";
            this.frequency = left.frequency + right.frequency;
        }

        public Node(Integer frequency, String letter) {
            this.frequency = frequency;
            this.letter = letter;
            this.order = "0";
        }

        public String getCode() {
            StringBuilder sb = new StringBuilder();
            sb.append(order);
            Node parentNode = parent;

            while (parentNode != null) {
                sb.append(parentNode.order);
                parentNode = parentNode.parent;
            }
            return sb.reverse().toString();
        }
    }


}
