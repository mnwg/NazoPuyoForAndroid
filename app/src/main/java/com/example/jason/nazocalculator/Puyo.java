package com.example.jason.nazocalculator;

/**
 * ぷよの種類
 */

enum Puyo {
    /** 赤ぷよ */
    RED(1, R.drawable.puyo_red),
    /** 青ぷよ */
    BLUE(2, R.drawable.puyo_blue),
    /** 緑ぷよ */
    GREEN(3, R.drawable.puyo_green),
    /** 黃ぷよ */
    YELLOW(4, R.drawable.puyo_yellow),
    /** 紫ぷよ */
    PURPLE(5, R.drawable.puyo_purple),
    /** おじゃまぷよ */
    OJAMA(-1, R.drawable.puyo_ojama),
    /** 固ぷよ */
    KATA(-2, R.drawable.puyo_kata),
    /** 空白 */
    NONE(0, R.drawable.puyo_none);

    /** 対応する数 */
    final int NUM;
    /** Drawableコード */
    private final int mDrawable;

    /** コンストラクタ */
    Puyo(final int num, final int drawable) {
        this.NUM = num;
        this.mDrawable = drawable;
    }

    /** Drawableコード取得 */
    int getDrawable() {
        return this.mDrawable;
    }
}
