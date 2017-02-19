package com.example.jason.nazocalculator;

/**
 * クリア条件
 */
class ClearCondition {

    /** 条件 */
    enum Condition {
        /** ぷよ全て消すべし */
        ALL("全消し", "ぷよ全て消すべし"),
        /** ?ぷよすべて消すべし */
        SOME_PUYO_ALL("色指定", "すべて消すべし"),
        /** ?連鎖するべし */
        CHAIN_NUM("連鎖指定", "連鎖するべし"),
        /** ?連鎖＆ぷよ全て消すべし */
        CHAIN_NUM_ALL("連鎖指定＋全消し", "連鎖＆ぷよ全て消すべし");

        /** タイトル */
        final String TITLE;
        /** 説明文 */
        final String MESSAGE;

        Condition(final String title, final String message) {
            TITLE = title;
            MESSAGE = message;
        }
    }

    /** 条件 */
    private final Condition mCondition;
    /** 条件に関連する数 */
    final int NUM;

    ClearCondition(final Condition condition, final int num) {
        mCondition = condition;
        NUM = num;
    }

    /** 条件を取得 */
    Condition getCondition() {
        return mCondition;
    }
}
