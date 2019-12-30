package com.github.bael;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * Класс для запуска алгоритма расчета поиска ближайших точек
 */
public class ClosestPointsDemo {

    public static void main(String[] args) {

        ClosestPointsDemo demo = new ClosestPointsDemo();
        Scanner sc = new Scanner(System.in);
        Point[] coordinates = demo.readCoordinates(sc);
        double distanceDiv = new ClosestPointsDivideAndConquer(coordinates).closestDistance();
        System.out.printf("%.4f\n", distanceDiv);
    }

    /**
     * Считываем координаты из пользовательского ввода
     *
     * @return массив точек
     */
    private Point[] readCoordinates(Scanner sc) {
        Objects.requireNonNull(sc, "Не заполнен сканер чтения данных!");
        // инициализируем массив координат
        int count = sc.nextInt();
        Point[] coordinates = new Point[count];

        for (int i = 0; i < count; i++) {
            long x = sc.nextLong();
            long y = sc.nextLong();
            coordinates[i] = new Point(x, y);
        }
        return coordinates;
    }

    // тестовый набор данных
    private Point[] produceTestCoordinatesSet() {
        Point[] points = new Point[11];
        points[0] = new Point(4, 4);
        points[1] = new Point(-2, -2);
        points[2] = new Point(-3, -4);
        points[3] = new Point(-1, 3);
        points[4] = new Point(2, 3);
        points[5] = new Point(-4, 0);
        points[6] = new Point(1, 1);
        points[7] = new Point(-1, -1);
        points[8] = new Point(3, -1);
        points[9] = new Point(-4, 2);
        points[10] = new Point(-2, 4);
        return points;
    }

    /***
     * Класс точки в двухмерном пространстве.
     * Умеет вычислять дистанцию до другой точки.
     */
    private static class Point {

        private long x;
        private long y;

        public Point(long x, long y) {
            this.x = x;
            this.y = y;
        }

        public static double distance(Point from, Point to) {
            double deltaX = (from.x - to.x);
            double deltaY = (from.y - to.y);
            return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        }

        public double distance(Point point) {
            return distance(this, point);
        }

        public long getX() {
            return x;
        }

        public long getY() {
            return y;
        }

        @Override
        public String toString() {
            return "{" + x + ", " + y + '}';
        }
    }

    /**
     * Базовый класс алгоритма расчета. Задает контракт - метод возвращающий минимальную дистанцию.
     */
    private static abstract class ClosestPoints {

        protected final Point[] points;

        public ClosestPoints(Point[] points) {
            Objects.requireNonNull(points, "Поданы пустые координаты!");
            this.points = points;
        }

        /***
         * Нахождение минимальной дистанции полным перебором
         * (для проверки основного алгоритма на тестовом наборе данных)
         * @param points - входные точки
         * @param l - левая граница
         * @param r - правая граница
         * @return минимальная дистанция
         */
        protected static double fullCombinationDistance(Point[] points, int l, int r) {
            if (l >= r) {
                return 0;
            }

            double minDistance = points[l].distance(points[l + 1]);
            for (int i = l; i < points.length - 1 && i < r; i++) {
                for (int j = i + 1; j < points.length && j < r; j++) {
                    double currentDistance = Point.distance(points[i], points[j]);
                    if (currentDistance < minDistance) {
                        minDistance = currentDistance;
                    }
                }
            }
            return minDistance;
        }

        public abstract double closestDistance();
    }

    /**
     * Класс расчета простым перебором  (O(n^2))
     */
    private static class ClosestPointsNaive extends ClosestPoints {

        public ClosestPointsNaive(Point[] points) {
            super(points);
        }

        public double closestDistance() {
            if (points.length <= 1) {
                return 0;
            }

            return fullCombinationDistance(points, 0, points.length - 1);
        }
    }

    /***
     * Класс расчета методом разделяй и властвуй, описанным в задании.
     */
    private static class ClosestPointsDivideAndConquer extends ClosestPoints {

        public ClosestPointsDivideAndConquer(Point[] points) {
            super(points);
        }

        public double closestDistance() {
            Arrays.sort(points, Comparator.comparingLong(o -> o.x));
            return closestDistance(points, 0, points.length - 1);
        }

        /***
         * Внутренний метод расчета минимального расстояния по поданному массиву точек и правой и левой границах,
         * внутри которых осуществляется поиск.
         * @param points массив входных точек
         * @param l левая граница для поиска
         * @param r правая граница для поиска
         * @return минимальное расстояние
         */
        private double closestDistance(Point[] points, int l, int r) {
            // сколько точек подряд проверяем по оси Y?
            int ySequenceCount = 7;
            //
            if (r - l > 3) {
                int m = (r + l) / 2;
                double leftDistance = closestDistance(points, l, m);
                double rightDistance = closestDistance(points, m + 1, r);
                double minD = Math.min(leftDistance, rightDistance);

                // Набор точек для сортировки по y координате
                List<Point> yList = new ArrayList<>();
                int rightX = m;
                double x = points[m].x;

                // отбираем все точки справа для набора в сортировку по y координате
                while (rightX <= r && (points[rightX].x - x < minD)) {
                    yList.add(points[rightX++]);
                }

                // отбираем все точки слева для набора в сортировку по y координате
                int leftX = m - 1;
                while (leftX >= l && (x - points[leftX].x < minD)) {
                    yList.add(points[leftX--]);
                }
                yList.sort(Comparator.comparingLong(o -> o.y));
                double minAdjPoint = minD;
                // по отсортированным по координате Y точкам ищем минимум расстояния среди
                // подмножества 7 ближайших точек к обрабатываемой в данный момент точке.
                for (int i = 0; i < yList.size() - 1; i++) {
                    Point p = yList.get(i);
                    for (int j = i + 1; j < yList.size()
                            && yList.get(j).y - yList.get(i).y < minAdjPoint
                            && j < i + ySequenceCount; j++) {
                        Point adjPoint = yList.get(j);
                        minAdjPoint = Math.min(minAdjPoint, p.distance(adjPoint));
                    }
                }
                return minAdjPoint;
            } else {
                return fullCombinationDistance(points, l, r);
            }
        }
    }
}
