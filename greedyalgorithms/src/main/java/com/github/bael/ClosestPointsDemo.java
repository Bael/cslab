package com.github.bael;

import java.util.*;

/**
 * Класс для запуска алгоритма расчета поиска ближайших точек
 */
public class ClosestPointsDemo {

    public static void main(String[] args) {

        ClosestPointsDemo demo = new ClosestPointsDemo();
        Scanner sc = new Scanner(System.in);
//        Point[] coordinates = demo.readCoordinates(sc);
        Point[] coordinates = demo.produceTestCoordinatesSet();
        double distanceDiv = new ClosestPointsDivideAndConquer(coordinates).closestDistance();
//        double distanceDiv = new ClosestPointsNaive(coordinates).closestDistance();
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
        //        Correct output:
        //        1.414213562

        Point[] points = new Point[5];
        points[0] = new Point(1, -1);
        points[1] = new Point(0, 4);
        points[2] = new Point(-5, -1);
        points[3] = new Point(2, 5);
        points[4] = new Point(3, 4);
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
     * Базовый класс алгоритма расчета минимального расстояния. Задает контракт - метод возвращающий минимальную дистанцию.
     */
    static abstract class ClosestPoints {

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
        protected static double bruteForceMinDistance(Point[] points, int l, int r) {
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
     * Класс расчета минимального расстояния простым перебором  (O(n^2))
     */
    static class ClosestPointsNaive extends ClosestPoints {
        public ClosestPointsNaive(Point[] points) {
            super(points);
        }
        public double closestDistance() {
            if (points.length <= 1) {
                return 0;
            }
            return bruteForceMinDistance(points, 0, points.length - 1);
        }
    }

    /***
     * Класс расчета минимального расстояния между точками
     * методом разделяй и властвуй, описанным в задании.
     */
    private static class ClosestPointsDivideAndConquer extends ClosestPoints {


        public ClosestPointsDivideAndConquer(Point[] points) {
            super(points);
        }

        /***
         * Количество точек на которые надо удалиться по вертикали
         * в отсортированном массиве чтобы понять мы вышли за текущую минимальную дистанцию d
         * (в одном квадрате длины d не может быть больше 4 точек (иначе расстояние между ними меньше d,
         * а мы уже убедились что это не так.)
         * (следовательно достаточно уйти на два квадрата (7 точек) наверх чтобы понять что минимума нам не видать
         * и можно переходить к следующей точке)
         */
        private static final int Y_SEQUENCE_COUNT = 7;

        public double closestDistance() {
            // лист точек с сортировкой по Y.
            // Важно копировать массив иначе список мутируется сортировкой массива.
            List<Point> pointsY = Arrays.asList(Arrays.copyOf(points, points.length));
            pointsY.sort(Comparator.comparingLong(o -> o.y));
            Arrays.sort(points, Comparator.comparingLong(o -> o.x));
            return closestDistanceRecursive(points, 0, points.length - 1, pointsY);
        }

        /***
         * Рекурсивный метод поиска минимального расстояния в подмассиве поданного массива
         * (по левой l и правой r границам)
         * @param points исходный массив точек, предусловие - отсортирован по X
         * @param l левая граница
         * @param r правая граница
         * @param pointsY список копия массива точек, предусловие - отсортирован по Y
         * @return
         */
        private double closestDistanceRecursive(Point[] points, int l, int r, List<Point> pointsY) {
            if (r - l > 3) {
                int m = (r + l) / 2;
                // Середина, по которой пойдет вертикальная линия
                // (массив points отсортирован по X, значит точки слева от m, меньше или равны middleX,
                // а справа больше или равны
                double middleX = points[m].x;
                List<Point> leftYPoints = new ArrayList<>();
                List<Point> rightYPoints = new ArrayList<>();
                for (Point point : pointsY) {
                    if (point.x < middleX) {
                        leftYPoints.add(point);
                    } else {
                        rightYPoints.add(point);
                    }
                }

                // находим минимальные растояние в левом и правом прямоугольнике, и минимум между ними
                double leftDistance = closestDistanceRecursive(points, l, m, leftYPoints);
                double rightDistance = closestDistanceRecursive(points, m + 1, r, rightYPoints);
                double minDistance = Math.min(leftDistance, rightDistance);

                // Набор точек для поиска по y координате
                List<Point> yList = new ArrayList<>();
                // проходя по отсортированному массиву добавляем в массив
                // для поиска по Y координате те точки,
                // что попадают в нашу полосу middleX - minD..middleX + minD
                for (Point point : pointsY) {
                    if (point.x > (middleX - minDistance) && point.x < (middleX + minDistance)) {
                        yList.add(point);
                    }
                }

//                yList.sort(Comparator.comparingLong(o -> o.y));

                // по отсортированным по координате Y точкам ищем минимум расстояния среди
                // подмножества 7 ближайших точек к обрабатываемой в данный момент точке.
                for (int i = 0; i < yList.size() - 1; i++) {
                    Point p = yList.get(i);
                    // для данной точки ищем минимальное расстояние у соседних выше по Y координате
                    for (int j = i + 1; j < yList.size()
//                            && yList.get(j).y - yList.get(i).y < minDistance
                            && j < i + Y_SEQUENCE_COUNT; j++) {
                        Point adjPoint = yList.get(j);
                        minDistance = Math.min(minDistance, p.distance(adjPoint));
                    }
                }
                return minDistance;
            } else {
                return bruteForceMinDistance(points, l, r);
            }
        }
    }
}
