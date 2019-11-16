package com.github.bael;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertArrayEquals;

public class CoverLineSegmentsWithPointsTest {
    @Test
    public void case1() {
        int[][] arr = new int[][]{{1, 3},
                {2, 5}, {3, 6}
        };

        CoverLineSegmentsWithPoints.Segment[] segs = init(arr);
        List<Integer> points = CoverLineSegmentsWithPoints.minPoints(segs);
        assertArrayEquals(new Integer[]{3}, points.toArray(new Integer[0]));
    }

    @Test
    public void case2() {
        int[][] arr = new int[][]{
                {4, 7},
                {1, 3},
                {2, 5},
                {5, 6},

        };

        CoverLineSegmentsWithPoints.Segment[] segs = init(arr);
        List<Integer> points = CoverLineSegmentsWithPoints.minPoints(segs);
        assertArrayEquals(new Integer[]{3, 6}, points.toArray(new Integer[0]));
    }


    private CoverLineSegmentsWithPoints.Segment[] init(int[][] input) {
        CoverLineSegmentsWithPoints.Segment[] arr = new CoverLineSegmentsWithPoints.Segment[input.length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new CoverLineSegmentsWithPoints.Segment(input[i][0], input[i][1]);
        }
        return arr;
    }
}