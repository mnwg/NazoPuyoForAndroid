package com.example.jason.nazocalculator;

/**
 * ぷよ操作一覧
 */
class PuyoController {

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
    // TODO: PuyoControllerというクラス名から、ぷよを操作するのは自明なので、メソッド名はputみたいなシンプルにしても良いと思います。
    static boolean putPuyo(final int[][] main, final int[] next, int place) {
        if (next[0] == 0 || next[1] == 0) {
            return false;
        }

        // TODO: 細かいロジックはわかりませんが、このswitch文、ifの方がシンプルになりそうに見えるのですが。。
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
        // TODO: 定義はメソッド内でしな方が良いです。
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
    static void dropPuyo(final int[][] field) {
        for (int x = 0; x < field.length; x++) {
            int empty = -1;

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
    static boolean deletePuyo(final int[][] main, final int[][] preField) {
        final int DELETE_NUM = 4;
        boolean isDelete = false;

        for (int x = 0; x < main.length; x++) {
            for (int y = 0; y < main[0].length; y++) {
                if (main[x][y] == preField[x][y] || main[x][y] <= 0) continue;

                final boolean[][] connect = new boolean[main.length][main[0].length];
                if (addConnectArray(main, connect, x, y, 1) >= DELETE_NUM) {
                    isDelete = true;
                    deletePointPuyo(main, connect);
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
}
