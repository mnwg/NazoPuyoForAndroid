package com.example.jason.nazocalculator;

/**
 * ぷよの種類
 */

enum Puyo {
    /** 赤ぷよ */
    RED("赤ぷよ", 1, R.drawable.puyo_red),
    /** 青ぷよ */
    BLUE("青ぷよ", 2, R.drawable.puyo_blue),
    /** 緑ぷよ */
    GREEN("緑ぷよ", 3, R.drawable.puyo_green),
    /** 黃ぷよ */
    YELLOW("黃ぷよ", 4, R.drawable.puyo_yellow),
    /** 紫ぷよ */
    PURPLE("紫ぷよ", 5, R.drawable.puyo_purple),
    /** おじゃまぷよ */
    OJAMA("おじゃまぷよ", -1, R.drawable.puyo_ojama),
    /** 固ぷよ */
    KATA("固ぷよ", -2, R.drawable.puyo_kata),
    /** 空白 */
    NONE("空白", 0, R.drawable.puyo_none);

    /** 名前 */
    final String NAME;
    /** 対応する数 */
    final int NUM;
    /** Drawableコード */
    private final int mDrawable;

    /** コンストラクタ */
    Puyo(final String name, final int num, final int drawable) {
        NAME = name;
        NUM = num;
        mDrawable = drawable;
    }

    /** Drawableコード取得 */
    int getDrawable() {
        return this.mDrawable;
    }
}
