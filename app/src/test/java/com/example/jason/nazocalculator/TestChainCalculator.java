package com.example.jason.nazocalculator;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * なぞぷよ実践テスト
 */
public class TestChainCalculator {

    private final int[][] main1 = {
            {0, 0, 0, 0, 0, 0, -1, -1, -1, -1},
            {0, 0, 0, 0, 0, 1, 1, 2, 2, -1},
            {0, 0, 0, 0, 0, 1, 3, 4, 2, -1},
            {0, 0, 0, 0, 0, 2, 3, 4, 1, -1},
            {0, 0, 0, 0, 0, 2, 3, 4, 1, -1},
            {0, 0, 0, 0, 0, 0, -1, -1, -1, -1}
    };
    private final int[] next1 = {3, 2, 1, 2, 1, 4, 1, 2};

    private final int[][] main2 = {
            {0, 2, 4, 2, 4, 4, 4, 2, 2, 2},
            {0, 0, 0, 2, -1, 1, 3, 1, 3, -1},
            {0, 0, 0, 2, 1, -1, 3, 1, -1, 3},
            {0, 0, 0, 4, 1, 3, -1, -1, 1, 3},
            {0, 0, 0, 4, -1, -1, -1, -1, -1, -1},
            {0, 4, 2, 4, 2, 2, 2, 4, 4, 4}
    };
    private final int[] next2 = {2, 4, 3, 3, 1, 1, 2, 4};

    private final int[][] main3 = {
            {0, 0, 0, 0, 1, -1, 1, -1, -1, -1},
            {0, 0, 0, 0, 0, -1, -1, 2, -1, -1},
            {0, 0, 0, 0, 0, 1, 3, 2, -1, -1},
            {0, 0, 0, 0, -1, 1, 1, 2, 3, -1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };
    private final int[] next3 = {3, 2, 2, 2, 1, 2, 1, 3, 1, 2};

    private final int[][] main4 = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1},
            {0, 0, 0, 2, 3, 4, 3, 3, 3, 2, -1, -1},
            {0, 0, 0, 2, 3, 4, 1, 1, 1, 2, -1, -1},
            {0, 0, 0, 2, 3, 4, 3, 3, 3, 2, -1, -1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1}
    };
    private final int[] next4 = {3, 3, 3, 3, 1, 3, 1, 2};

    private final int[][] main5 = {
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {0, 0, 0, 0, 0, 0, 2, 2, 4, 1},
            {0, 0, 0, 0, 3, 3, 1, 2, 4, 4},
            {0, 0, 0, 0, 3, 1, 1, 4, 2, 2},
            {0, 0, 0, 0, 0, 0, 4, 4, 2, 3},
            {0, 0, 0, 0, 0, 0, 0, 0, 3, 3}
    };
    private final int[] next5 = {2, 1, 4, 3, 3, 1, 2, 4};

    private final int[][] main6 = {
            {0, 0, 0, 0, 1, 1, 3, 3, 3, -1},
            {0, 0, 0, 0, 0, 0, 0, 1, -1, -1},
            {0, 0, 0, 0, 2, 1, 2, 2, -1, -1},
            {0, 0, 0, 0, 0, 2, -1, -1, -1, -1},
            {0, 0, 0, 0, 0, 0, 3, 1, 1, -1},
            {0, 0, 0, 0, 0, 0, 3, 3, -1, -1}
    };
    private final int[] next6 = {1, 2, 2, 2, 1, 3, 3, 2};

    private final int[][] main7 = {
            {0, 0, 0, 0, 0, 0, 0, 4, 4, 2},
            {0, 0, 0, 0, 0, 0, 0, 2, 4, 2},
            {0, 0, 0, 1, 2, 1, 2, 1, 2, 1},
            {0, 0, 0, 2, 1, 2, 1, 2, 1, 2},
            {0, 0, 0, 0, 0, 0, 0, 1, 4, 1},
            {0, 0, 0, 0, 0, 0, 0, 4, 4, 1}
    };
    private final int[] next7 = {1, 4, 2, 4, 1, 4, 2, 4};

    private final int[][] main8 = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, -1, -1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, -1, -1},
            {0, 0, 0, 0, 0, 2, 3, 2, -1, 1, -1, -1},
            {0, 0, 0, 0, 3, 2, 3, 2, 2, 3, -1, -1},
            {0, 0, 0, 0, 0, 0, 1, 1, 1, 3, -1, -1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1}
    };
    private final int[] next8 = {1, 3, 2, 2, 3, 3, 2, 1};

    @Test
    public void test() throws Exception {
        long start = System.currentTimeMillis();
        final int[] answer1 = ChainCalculator.getAllAnswerNodes(main1, next1);
        long end = System.currentTimeMillis();
        System.out.println("1: " + (end - start));

        assertEquals(answer1[0], 5);
        assertEquals(answer1[1], 4);
        assertEquals(answer1[2], 5);
        assertEquals(answer1[3], 9);

        start = System.currentTimeMillis();
        final int[] answer2 = ChainCalculator.getAllAnswerNodes(main2, next2);
        end = System.currentTimeMillis();
        System.out.println("2: " + (end - start));

        assertEquals(answer2[0], 10);
        assertEquals(answer2[1], 15);
        assertEquals(answer2[2], 12);
        assertEquals(answer2[3], 8);

        start = System.currentTimeMillis();
        final int[] answer3 = ChainCalculator.getPuyoAllAnswerNodes(main3, next3, -1);
        end = System.currentTimeMillis();
        System.out.println("3: " + (end - start));

        assertEquals(answer3[0], 0);
        assertEquals(answer3[1], 2);
        assertEquals(answer3[2], 1);
        assertEquals(answer3[3], 1);
        assertEquals(answer3[4], 13);

        start = System.currentTimeMillis();
        final int[] answer4 = ChainCalculator.getPuyoAllAnswerNodes(main4, next4, 1);
        end = System.currentTimeMillis();
        System.out.println("4: " + (end - start));

        assertEquals(answer4[0], 0);
        assertEquals(answer4[1], 12);
        assertEquals(answer4[2], 18);
        assertEquals(answer4[3], 18);

        start = System.currentTimeMillis();
        final int[] answer5 = ChainCalculator.getChainAnswerNodes(main5, next5, 8);
        end = System.currentTimeMillis();
        System.out.println("5: " + (end - start));

        assertEquals(answer5[0], 10);
        assertEquals(answer5[1], 8);
        assertEquals(answer5[2], 3);
        assertEquals(answer5[3], 7);

        start = System.currentTimeMillis();
        final int[] answer6 = ChainCalculator.getChainAnswerNodes(main6, next6, 6);
        end = System.currentTimeMillis();
        System.out.println("6: " + (end - start));

        assertEquals(answer6[0], 15);
        assertEquals(answer6[1], 13);
        assertEquals(answer6[2], 15);
        assertEquals(answer6[3], 1);

        start = System.currentTimeMillis();
        final int[] answer7 = ChainCalculator.getChainAllAnswerNodes(main7, next7, 8);
        end = System.currentTimeMillis();
        System.out.println("7: " + (end - start));

        assertEquals(answer7[0], 8);
        assertEquals(answer7[1], 20);
        assertEquals(answer7[2], 10);
        assertEquals(answer7[3], 16);

        start = System.currentTimeMillis();
        final int[] answer8 = ChainCalculator.getChainAllAnswerNodes(main8, next8, 6);
        end = System.currentTimeMillis();
        System.out.println("8: " + (end - start));

        assertEquals(answer8[0], 3);
        assertEquals(answer8[1], 13);
        assertEquals(answer8[2], 15);
        assertEquals(answer8[3], 0);
    }
}