package com.example.jason.nazocalculator;

/**
 * ぷよの種類
 */

enum Puyo {
         // TODO: 多言語化考慮するなら、文字列はresに定義する
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
        // TODO: publicの定数っぽい名称と、privateの変数っぽい名称でgetter用意の差が、なんかしっくりきません。
        // 個人的には、enumは定義みたいなものなので、publicの定数っぽい名称で、フィールド直接参照でもいいかなと思ってます。

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
