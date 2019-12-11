package com.github.bael;


import java.util.*;

public class ClosestPointsDemo {

    public static void main(String[] args) {

        ClosestPointsDemo demo = new ClosestPointsDemo();
        Scanner sc = new Scanner(System.in);

//        Point[] coordinates = demo.produceFromInput(sc);
        Point[] coordinates = demo.produceFromRandom4();
        double distance = new ClosestPointsNaive(coordinates).closestDistance();
        System.out.println(distance);

        double distanceDiv = new ClosestPointsDivideAndConquer(coordinates).closestDistance();
        System.out.println(distanceDiv);

//        double distanceDiv2 = new ClosestPointsDivideAndConquer(demo.produceFromRandom2()).closestDistance();
//        System.out.println(Double.compare(distanceDiv2, 2.828427125));
//        System.out.printf("%.4f\n", distanceDiv2);
//        System.out.println(distanceDiv2);



    }

    private static double fullCombinationDistance(Point[] points, int l, int r) {
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

    private Point[] produceFromRandom() {
        Point[] points = new Point[10000];
        Random a = new Random();
        int boundX = 100000;
        int boundY = 100000;
        for (int i=0; i< points.length; i++) {
            if (i % 7 == 0) {
                points[i] = new Point(-a.nextInt(boundX), -a.nextInt(boundY));
            } else {
                if (i % 3 == 7) {
                    points[i] = new Point(-a.nextInt(boundX), a.nextInt(boundY));
                }
                 else {
                    points[i] = new Point(a.nextInt(boundX), a.nextInt(boundY));
                }
            }
        }
        return points;
    }

    private Point[] produceFromRandom3() {
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

    private Point[] produceFromRandom2() {
        Point[] points = new Point[4];
        points[0] = new Point(0, 0);
        points[1] = new Point(5, 6);
        points[2] = new Point(3, 4);
        points[3] = new Point(7, 2);
        return points;
    }

    private Point[] produceFromRandom4() {
        Point[] points = new Point[5];
//        Correct output:
//        1.414213562

        points[0] = new Point(1, -1);
        points[1] = new Point(0, 4);
        points[2] = new Point(-5, -1);
        points[3] = new Point(2, 5);
        points[4] = new Point(3, 4);
        return points;
    }



    private Point[] produceFromInput(Scanner sc) {
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

    private static abstract class ClosestPoints {
        protected final Point[] points;

        public ClosestPoints(Point[] points) {
            Objects.requireNonNull(points, "Поданы пустые координаты!");
            this.points = points;
        }

        public abstract double closestDistance();
    }

    private static class ClosestPointsNaive extends ClosestPoints {
        public ClosestPointsNaive(Point[] points) {
            super(points);
        }

        public double closestDistance() {
            if (points.length <= 1) {
                return 0;
            }

            double minDistance = this.points[0].distance(this.points[1]);
            for (int i = 0; i < this.points.length; i++) {
                for (int j = i + 1; j < this.points.length; j++) {
                    double currentDistance = Point.distance(points[i], points[j]);
                    if (currentDistance < minDistance) {
                        minDistance = currentDistance;
                    }
                }
            }
            return minDistance;
        }
    }

    private static class ClosestPointsDivideAndConquer extends ClosestPoints {
        public ClosestPointsDivideAndConquer(Point[] points) {
            super(points);
        }

        public double closestDistance() {
            Arrays.sort(points, Comparator.comparingLong(o -> o.x));
            return divide(points, 0, points.length - 1);
        }

        private double divide(Point[] points, int l, int r) {
//            System.out.printf("Divide called with points %s and l %s and r %s \n",
//                    Arrays.toString(points), l, r);
            if (r - l > 3) {
                int m = (r + l) / 2;
                double leftDistance = divide(points, l, m);
                double rightDistance = divide(points, m + 1, r);
                double minD = Math.min(leftDistance, rightDistance);

                // find range:
                List<Point> yList = new ArrayList<>();
                int rightX = m;
                double x = points[m].x;
                // отбираем все точки справа
                while (rightX < r && (points[rightX].x - x < minD)) {
                    yList.add(points[rightX++]);
                }

                // отбираем все точки слева
                int leftX = m - 1;
                while (leftX > l && (x - points[leftX].x < minD)) {
                    yList.add(points[leftX--]);
                }
                yList.sort(Comparator.comparingLong(o -> o.y));
                double minAdjPoint = minD;

//                Point[] yArray = yList.toArray(new Point[0]);
                for (int i = 0; i < yList.size() - 1; i++) {
                    Point p = yList.get(i);
                    for (int j = i + 1; j < yList.size() && j < i + 7; j++) {
                        Point adjPoint = yList.get(j);
                        minAdjPoint = Math.min(minAdjPoint, p.distance(adjPoint));
                    }
                }

                return minAdjPoint;
            } else {
//                System.out.println("Closed recursion!");
                return fullCombinationDistance(points, l, r);
            }

        }
    }


}
