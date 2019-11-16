package com.github.bael;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/***
 * Дано N Отрезков, найти минимальный набор точек затрагивающих все отрезки
 */
public class CoverLineSegmentsWithPoints {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int count = sc.nextInt();
        Segment[] segments = new Segment[count];
        for (int i = 0; i < count; i++) {
            int l = sc.nextInt();
            int r = sc.nextInt();
            segments[i] = new Segment(l, r);
        }

        List<Integer> points = minPoints(segments);

        System.out.println(points.size());
        points.forEach(integer -> System.out.printf("%d ", integer));
    }

    /**
     * Считаем что надежный шаг содержит отрезок с минимальной правой стороной, берем ее за первую точку и отсекаем все отрезки которые задеты этой точкой.
     * потом повторяем сначала
     * Отрезки предварительно сортируем
     * @param segs отрезки
     * @return возвращается минимальный список точек затрагивающих поданные отрезки
     */
    public static List<Integer> minPoints(Segment[] segs) {
        Arrays.sort(segs);

        List<Integer> points = new ArrayList<>();
//        System.out.println(Arrays.toString(segs));

        int point = segs[0].r;
        points.add(point);
//        System.out.println("point is " + point);
        for (int i = 1; i < segs.length; i++) {
            if (segs[i].l > point) {
                point = segs[i].r;
                points.add(point);
//                System.out.println("point is " + point);
            }
        }

        return points;
    }

    static class Segment implements Comparable<Segment> {
        private int l;
        private int r;

        Segment(int l, int r) {
            this.l = l;
            this.r = r;
        }

        @Override
        public int compareTo(Segment o) {
            return Integer.compare(r, o.r);
        }

        @Override
        public String toString() {
            return l + " " + r;
        }
    }
}
