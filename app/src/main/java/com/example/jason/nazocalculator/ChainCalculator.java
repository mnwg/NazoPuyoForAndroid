package com.example.jason.nazocalculator;

import java.util.ArrayList;

/**
 * 連鎖配列を計算
 */
class ChainCalculator {

    private int[][] mMainField;
    private int[] mNextField;
    private ClearCondition mClearCondition;

    ChainCalculator(final int[][] main, final int[] next, final ClearCondition clearCondition) {
        mMainField = copyArray(main);
        mNextField = copyArray(next);
        mClearCondition = clearCondition;
    }

    /**
     * 連鎖数最大となる連鎖配列を返却
     *
     * @return 連鎖配列
     */
    ArrayList<int[][]> getAnswer() {
        final ArrayList<int[][]> answer = new ArrayList<>();
        final int[] nodes = getAnswerNodes(mMainField, mNextField, mClearCondition);

        if (nodes == null) return null;

        answer.add(copyArray(mMainField));
        for (int index : nodes) {
            PuyoController.putPuyo(mMainField, mNextField, index);
            answer.add(copyArray(mMainField));

            while (true) {
                final int[][] preField = copyArray(mMainField);
                PuyoController.dropPuyo(mMainField);
                answer.add(copyArray(mMainField));
                if (PuyoController.deletePuyo(mMainField, preField)) {
                    answer.add(copyArray(mMainField));
                } else {
                    break;
                }
            }
        }
        return answer;
    }

    /**
     * クリア条件を満たすノードを返却
     *
     * @param main      メイン配列
     * @param next      ネクスト
     * @param condition クリア条件
     * @return クリア条件を満たすノード
     */
    static int[] getAnswerNodes(final int[][] main, final int[] next, final ClearCondition condition) {
        final int maxNodeNum = 21;
        final int[] nodes = new int[next.length / 2];
        final int clearNum = condition.NUM;
        final ClearCondition.Condition clearCondition = condition.getCondition();
        boolean isAnswer = false;

        search:
        while (nodes[0] <= maxNodeNum) {
            final int[][] subMain = copyArray(main);
            final int[] subNext = copyArray(next);

            for (int index = 0; index < nodes.length; index++) {
                PuyoController.putPuyo(subMain, subNext, nodes[index]);
                final int subChainNum = getChainNum(subMain);

                switch (clearCondition) {
                    case ALL:
                        if (isAllClear(subMain)) {
                            isAnswer = true;
                            break search;
                        }
                        break;
                    case SOME_PUYO_ALL:
                        if (isColorClear(subMain, clearNum)) {
                            isAnswer = true;
                            break search;
                        }
                        break;
                    case CHAIN_NUM:
                        if (subChainNum >= clearNum) {
                            isAnswer = true;
                            break search;
                        }
                        break;
                    case CHAIN_NUM_ALL:
                        if (subChainNum >= clearNum && isAllClear(subMain)) {
                            isAnswer = true;
                            break search;
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("ClearCondition is not correct");
                }

//                途中で連鎖する問題は解けませんが、枝切して計算が高速になります。
//                if (subChainNum > 0) {
//                    stepNextNode(nodes, index);
//                    continue search;
//                }
            }
            stepNextNode(nodes, nodes.length - 1);
        }

        if (!isAnswer) return null;

        return nodes;
    }

    /**
     * 探索順番を保持する配列の指定位置に加算
     * <p>
     * 深さ優先探索
     *
     * @param nodes 探索順番を保持する配列
     * @param index 探索を変化させる場所
     */
    private static void stepNextNode(final int[] nodes, final int index) {
        final int MAX_NODE_NUM = 21;

        nodes[index]++;
        for (int i = index; i > 0; i--) {
            if (nodes[i] > MAX_NODE_NUM) {
                nodes[i] = 0;
                nodes[i - 1]++;
            }
        }
    }

    /**
     * 指定フィールドの連鎖数を返却
     * <p>
     * 連鎖が起きていない場合{@code 0}を返却
     *
     * @param main メイン配列
     * @return 連鎖数
     */
    private static int getChainNum(final int[][] main) {
        int chainNum = 0;

        while (true) {
            final int[][] preField = copyArray(main);
            PuyoController.dropPuyo(main);
            if (PuyoController.deletePuyo(main, preField)) {
                chainNum++;
            } else {
                break;
            }
        }
        return chainNum;
    }

    /**
     * フィールドが全消し状態かどうか
     *
     * @param field フィールド
     * @return 全消し状態
     */
    private static boolean isAllClear(final int[][] field) {
        for (int x = 0; x < field.length; x++) {
            for (int y = 0; y < field[0].length; y++) {
                if (field[x][y] != 0) return false;
            }
        }
        return true;
    }

    /**
     * フィールドに指定の色がないかどうか
     *
     * @param field フィールド
     * @param color 指定の色
     * @return 指定の色がないかどうか
     */
    private static boolean isColorClear(final int[][] field, final int color) {
        for (int x = 0; x < field.length; x++) {
            for (int y = 0; y < field[0].length; y++) {
                if (field[x][y] == color) return false;
            }
        }
        return true;
    }

    /**
     * int配列をコピー
     *
     * @param copy コピー元
     * @return コピー後
     */
    private static int[] copyArray(final int[] copy) {
        final int[] array = new int[copy.length];

        System.arraycopy(copy, 0, array, 0, copy.length);
        return array;
    }

    /**
     * int配列をコピー
     *
     * @param copy コピー元
     * @return コピー後
     */
    private static int[][] copyArray(final int[][] copy) {
        final int[][] array = new int[copy.length][copy[0].length];

        for (int x = 0; x < copy.length; x++) {
            System.arraycopy(copy[x], 0, array[x], 0, copy[0].length);
        }
        return array;
    }
}
