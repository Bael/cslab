package com.github.bael.csprogram;

/**
 * Подсчет максимальной высоты дерева.
 * Формат данных - две строки, первая с количеством элементов. вторая с числами представляющими родителей элементов
 * соответствующих индексу нового токена строки.
 */
public class TreeHeight {
    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        int count = sc.nextInt();
//        int[] tree = new int[count];
//        for (int i=0; i<count; i++) {
//            tree[i] = sc.nextInt();
//        }
//        System.out.println(treeHeight(tree));

        System.out.println(treeHeight(new int[]{9, 7, 5, 5, 2, 9, 9, 9, 2, -1}));

    }


    private static int treeHeight(int[] tree) {
        int maxHeight = 0;
        for (int i = 0; i < tree.length; i++) {
            maxHeight = Math.max(calcParentHeight(tree, i), maxHeight);
        }
        return maxHeight;
    }

    private static int calcParentHeight(int[] tree, int element) {
        int count = 1;
        while (tree[element] != -1) {
            element = tree[element];
            count++;
        }
        return count;
    }


}
