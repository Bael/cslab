package com.github.bael;


import java.util.*;

public class PointsAndSegments {

    public static void main(String[] args) {
        PointsAndSegments demo = new PointsAndSegments();
        Scanner sc = new Scanner(System.in);
//        run(sc);
        runExp();
    }

    private static void runExp() {
        long[] start = new long[]{0, 12, 12};
        long[] finish = new long[]{7, 12, 20};
        long[] point = new long[]{1, 6, 11, 12, 13, 20, 21};
        System.out.println(Arrays.toString(start));
        System.out.println(Arrays.toString(finish));
        System.out.println(Arrays.toString(point));
        System.out.println(Arrays.toString(calcIntersections(start, finish, point)));
    }

    private static void run(Scanner sc) {
        // read data
        Objects.requireNonNull(sc, "Не заполнен сканер чтения данных!");
        // инициализируем массив координат
        int segmentCount = sc.nextInt();
        int pointCount = sc.nextInt();

        long[] segmentStartPoints = new long[segmentCount];
        long[] segmentFinishPoints = new long[segmentCount];

        for (int i = 0; i < segmentCount; i++) {
            long start = sc.nextLong();
            long finish = sc.nextLong();
            segmentStartPoints[i] = start;
            segmentFinishPoints[i] = finish;
        }

        long[] points = new long[pointCount];
        for (int i = 0; i < pointCount; i++) {
            points[i] = sc.nextLong();
        }

        // end of read data
        // calc result
        int[] pointInSegmentCount = calcIntersections(segmentStartPoints, segmentFinishPoints, points);
        for (int i = 0; i < pointInSegmentCount.length; i++) {
            System.out.printf("%s ", pointInSegmentCount[i]);
        }
        System.out.println();
    }

    private static int[] calcIntersections(long[] segmentStartPoints, long[] segmentFinishPoints, long[] points) {
        int[] intersections = new int[points.length];
        Arrays.sort(segmentStartPoints);
        Arrays.sort(segmentFinishPoints);
//        System.out.println(Arrays.toString(segmentStartPoints));
//        System.out.println(Arrays.toString(segmentFinishPoints));
        long[] sortedPoints = Arrays.copyOf(points, points.length);

        Arrays.sort(sortedPoints);
        Map<Long, Integer> counts = new HashMap<>();

        int depth = 0;
        int startIndex = 0;
        int finishIndex = 0;
        int pointIndex = 0;
//        System.out.println(Arrays.toString(segmentStartPoints));
//        System.out.println(Arrays.toString(segmentFinishPoints));
//        System.out.println(Arrays.toString(points));
        while (pointIndex < points.length) {
            long point = sortedPoints[pointIndex];
            while (segmentStartPoints.length > startIndex
                    && point >= segmentStartPoints[startIndex]) {
                depth++;
//                System.out.printf("inc depth to %s at %s with start %s \n", depth, point,
//                    segmentStartPoints[startIndex]);
                startIndex++;

            }
            while (finishIndex < segmentFinishPoints.length
                    && point > segmentFinishPoints[finishIndex]) {

//                System.out.printf("dec depth to %s at %s with finishIndex %s\n", depth, point,
//                    finishIndex);
                depth--;
                finishIndex++;
            }
            counts.put(point, depth);
            pointIndex++;
        }

        for (int i = 0; i < points.length; i++) {
            intersections[i] = counts.get(points[i]);
        }
        return intersections;
    }

}
