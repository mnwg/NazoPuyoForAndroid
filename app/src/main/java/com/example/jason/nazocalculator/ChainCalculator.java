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
        final int[] nodes;
        switch (mClearCondition.getCondition()) {
            case ALL:
                nodes = getAllAnswerNodes(mMainField, mNextField);
                break;
            case SOME_PUYO_ALL:
                nodes = getPuyoAllAnswerNodes(mMainField, mNextField, mClearCondition.getNum());
                break;
            case CHAIN_NUM:
                nodes = getChainAnswerNodes(mMainField, mNextField, mClearCondition.getNum());
                break;
            case CHAIN_NUM_ALL:
                nodes = getChainAllAnswerNodes(mMainField, mNextField, mClearCondition.getNum());
                break;
            default:
                return null;
        }

        answer.add(mMainField);
        for (int index : nodes) {
            putPuyo(mMainField, mNextField, index);
            answer.add(mMainField);

            while (true) {
                final int[][] preField = copyArray(mMainField);
                dropPuyo(mMainField);
                answer.add(mMainField);
                if (deletePuyo(mMainField, preField)) {
                    answer.add(mMainField);
                } else {
                    break;
                }
            }
        }
        return answer;
    }

    /*
        クリア条件ごとの連鎖計算
     */

    /**
     * 全消し状態となるノードを返却
     *
     * @return 全消し状態となるノード
     */
    static int[] getAllAnswerNodes(final int[][] main, final int[] next) {
        final int maxNodeNum = 21;
        final int[] nodes = new int[next.length / 2];
        int[] maxNodes = new int[nodes.length];
        int[][] mainField = copyArray(main);
        int[] nextField = copyArray(next);

        search:
        while (nodes[0] <= maxNodeNum) {
            for (int index = 0; index < nodes.length; index++) {
                putPuyo(mainField, nextField, nodes[index]);
                getChainNum(mainField);

                if (isAllClear(mainField)) {
                    maxNodes = copyArray(nodes);
                    break search;
                }
            }
            mainField = copyArray(main);
            nextField = copyArray(next);
            stepNextNode(nodes, nodes.length - 1);
        }
        return maxNodes;
    }

    /**
     * 指定の色が全消しとなるノードを返却
     *
     * @return 指定の色が全消しとなるノード
     */
    static int[] getPuyoAllAnswerNodes(final int[][] main, final int[] next, final int color) {
        final int maxNodeNum = 21;
        final int[] nodes = new int[next.length / 2];
        int[] maxNodes = new int[nodes.length];
        int[][] mainField = copyArray(main);
        int[] nextField = copyArray(next);

        search:
        while (nodes[0] <= maxNodeNum) {
            for (int index = 0; index < nodes.length; index++) {
                putPuyo(mainField, nextField, nodes[index]);
                getChainNum(mainField);

                if (isColorClear(mainField, color)) {
                    maxNodes = copyArray(nodes);
                    break search;
                }
            }
            mainField = copyArray(main);
            nextField = copyArray(next);
            stepNextNode(nodes, nodes.length - 1);
        }
        return maxNodes;
    }

    /**
     * 指定の連鎖数となるノードを返却
     *
     * @return 指定の連鎖数となるノード
     */
    static int[] getChainAnswerNodes(final int[][] main, final int[] next, final int chainNum) {
        final int maxNodeNum = 21;
        final int[] nodes = new int[next.length / 2];
        int[] maxNodes = new int[nodes.length];
        int[][] mainField = copyArray(main);
        int[] nextField = copyArray(next);

        search:
        while (nodes[0] <= maxNodeNum) {
            for (int index = 0; index < nodes.length; index++) {
                putPuyo(mainField, nextField, nodes[index]);
                final int subChainNum = getChainNum(mainField);

                if (subChainNum > 0 && subChainNum >= chainNum) {
                    maxNodes = copyArray(nodes);
                    break search;
                }
            }
            mainField = copyArray(main);
            nextField = copyArray(next);
            stepNextNode(nodes, nodes.length - 1);
        }
        return maxNodes;
    }

    /**
     * 指定の連鎖数＋全消し状態となるノードを返却
     *
     * @return 指定の連鎖数＋全消し状態となるノード
     */
    static int[] getChainAllAnswerNodes(final int[][] main, final int[] next, final int chainNum) {
        final int maxNodeNum = 21;
        final int[] nodes = new int[next.length / 2];
        int[] maxNodes = new int[nodes.length];
        int[][] mainField = copyArray(main);
        int[] nextField = copyArray(next);

        search:
        while (nodes[0] <= maxNodeNum) {
            for (int index = 0; index < nodes.length; index++) {
                putPuyo(mainField, nextField, nodes[index]);
                final int subChainNum = getChainNum(mainField);

                if (subChainNum > 0 && subChainNum >= chainNum && isAllClear(mainField)) {
                    maxNodes = copyArray(nodes);
                    break search;
                }
            }
            mainField = copyArray(main);
            nextField = copyArray(next);
            stepNextNode(nodes, nodes.length - 1);
        }
        return maxNodes;
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

    /*
        その他メソッド
     */

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
            dropPuyo(main);
            if (deletePuyo(main, preField)) {
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
                if (field[x][y] != 0) {
                    return false;
                }
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
                if (field[x][y] == color) {
                    return false;
                }
            }
        }
        return true;
    }

    /*
        ぷよ操作メソッド
     */

    /**
     * ぷよを指定位置に設置。設置できない場合{@code false}を返却
     * <p>
     * ネクスト配列も更新
     *
     * @param main  メイン配列
     * @param next  ネクスト配列
     * @param place 設置場所
     * @return 設置したかを返却
     */
    private static boolean putPuyo(final int[][] main, final int[] next, int place) {
        if (next[0] == 0 || next[1] == 0) {
            return false;
        }

        switch (place) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                if (main[place][0] == 0 && main[place][1] == 0) {
                    main[place][0] = next[0];
                    main[place][1] = next[1];
                    stepNextArray(next);
                    return true;
                }
                break;
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                place -= 6;
                if (main[place][0] == 0 && main[place][1] == 0) {
                    main[place][0] = next[1];
                    main[place][1] = next[0];
                    stepNextArray(next);
                    return true;
                }
                break;
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
                place -= 12;
                if (main[place][0] == 0 && main[place + 1][0] == 0) {
                    main[place][0] = next[0];
                    main[place + 1][0] = next[1];
                    stepNextArray(next);
                    return true;
                }
                break;
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
                place -= 17;
                if (main[place][0] == 0 && main[place + 1][0] == 0) {
                    main[place][0] = next[1];
                    main[place + 1][0] = next[0];
                    stepNextArray(next);
                    return true;
                }
                break;
            default:
                throw new IllegalArgumentException("put place is incorrect");
        }
        return false;
    }

    /**
     * ネクスト配列更新
     *
     * @param next ネクスト配列
     */
    private static void stepNextArray(final int[] next) {
        final int NEXT_NUM = 2;

        System.arraycopy(next, 2, next, 0, next.length - NEXT_NUM);
        next[next.length - 2] = 0;
        next[next.length - 1] = 0;
    }

    /**
     * ぷよを落下
     *
     * @param field フィールド
     */
    private static void dropPuyo(final int[][] field) {
        int empty;

        for (int x = 0; x < field.length; x++) {
            empty = -1;

            for (int y = field[0].length - 1; y >= 0; y--) {
                if (empty != -1 && field[x][y] != 0) {
                    field[x][empty] = field[x][y];
                    field[x][y] = 0;
                    empty--;
                } else if (empty == -1 && field[x][y] == 0) {
                    empty = y;
                }
            }
        }
    }

    /**
     * 4連結以上のぷよを消去。消去していない場合{@code false}を返却
     * <p>
     * おじゃまぷよも巻き込んで消去
     *
     * @param main メイン配列
     * @return 消去したかを返却
     */
    private static boolean deletePuyo(final int[][] main, final int[][] preField) {
        final int DELETE_NUM = 4;
        boolean isDelete = false;
        boolean[][] connect;

        for (int x = 0; x < main.length; x++) {
            for (int y = 0; y < main[0].length; y++) {
                if (main[x][y] > 0 && main[x][y] != preField[x][y]) {
                    connect = new boolean[main.length][main[0].length];
                    if (addConnectArray(main, connect, x, y, 1) >= DELETE_NUM) {
                        isDelete = true;
                        deletePointPuyo(main, connect);
                    }
                }
            }
        }
        return isDelete;
    }

    /**
     * 連結配列にそってぷよを消去
     * <p>
     * まわりのおじゃまぷよも消去
     *
     * @param main    メイン配列
     * @param connect 連結配列
     */
    private static void deletePointPuyo(final int[][] main, final boolean[][] connect) {
        for (int x = 0; x < main.length; x++) {
            for (int y = 0; y < main[0].length; y++) {
                if (connect[x][y]) {
                    main[x][y] = 0;
                    deleteOjama(main, x, y);
                }
            }
        }
    }

    /**
     * 指定位置の連結配列を作成
     *
     * @param main    メイン配列
     * @param connect 連結配列
     * @param x       指定横位置
     * @param y       指定縦位置
     * @param count   連結数カウント
     * @return 連結数
     */
    private static int addConnectArray(final int[][] main, final boolean[][] connect, final int x, final int y, int count) {
        connect[x][y] = true;

        // 左方向チェック
        if (x > 0 && main[x - 1][y] == main[x][y] && !connect[x - 1][y]) {
            count = addConnectArray(main, connect, x - 1, y, count + 1);
        }
        // 右方向チェック
        if (x < main.length - 1 && main[x + 1][y] == main[x][y] && !connect[x + 1][y]) {
            count = addConnectArray(main, connect, x + 1, y, count + 1);
        }
        // 上方向チェック
        // 13段目考慮
        if (y > 1 && main[x][y - 1] == main[x][y] && !connect[x][y - 1]) {
            count = addConnectArray(main, connect, x, y - 1, count + 1);
        }
        // 下方向チェック
        if (y < main[0].length - 1 && main[x][y + 1] == main[x][y] && !connect[x][y + 1]) {
            count = addConnectArray(main, connect, x, y + 1, count + 1);
        }
        return count;
    }

    /**
     * 指定位置と隣接するおじゃまぷよを消去
     * <p>
     * 固ぷよはおじゃまぷよに変換
     *
     * @param main メイン配列
     * @param x    指定横位置
     * @param y    指定縦位置
     */
    private static void deleteOjama(final int[][] main, final int x, final int y) {
        // おじゃまぷよが-1、固ぷよが-2とする場合

        // 上方向チェック
        // 13段目考慮
        if (y > 1 && main[x][y - 1] < 0) {
            main[x][y - 1]++;
        }
        // 下方向チェック
        if (y < main[0].length - 1 && main[x][y + 1] < 0) {
            main[x][y + 1]++;
        }
        // 左方向チェック
        if (x > 0 && main[x - 1][y] < 0) {
            main[x - 1][y]++;
        }
        // 右方向チェック
        if (x < main.length - 1 && main[x + 1][y] < 0) {
            main[x + 1][y]++;
        }
    }

    /*
        配列操作メソッド
     */

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
