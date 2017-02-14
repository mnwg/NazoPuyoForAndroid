package com.example.jason.nazocalculator;

/**
 * クリア条件
 */
class ClearCondition {

    /** 条件 */
    enum Condition {
        /** ぷよ全て消すべし */
        ALL,
        /** ?ぷよすべて消すべし */
        SOME_PUYO_ALL,
        /** ?連鎖するべし */
        CHAIN_NUM,
        /** ?連鎖＆ぷよ全て消すべし */
        CHAIN_NUM_ALL,
    }

    private Condition mCondition;
    private int mNum;

    ClearCondition(final Condition condition, final int num) {
        mCondition = condition;
        mNum = num;
    }

    Condition getCondition() {
        return mCondition;
    }

    int getNum() {
        return mNum;
    }
}
