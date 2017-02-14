package com.example.jason.nazocalculator;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AlertDialogFragment.OnDialogClickListener {

    private static final int MAIN_WIDTH = 6;
    private static final int MAIN_HEIGHT = 13;
    private static final int NEXT_WIDTH = 2;
    private static final int NEXT_HEIGHT = 10;
    private static final int REQUEST_CODE_GET_CHAIN = 0;
    private static final int REQUEST_CODE_DELETE = 1;
    private Puyo[][] mMainField = new Puyo[MAIN_WIDTH][MAIN_HEIGHT];
    private Puyo[][] mNextField = new Puyo[NEXT_WIDTH][NEXT_HEIGHT];
    private Puyo mCheckedPuyo = Puyo.NONE;
    private ClearCondition mClearCondition;
    private int mAnswerIndex = 0;
    private int[][][] mAnswer;

    private LinearLayout mMainLayout;
    private LinearLayout mNextLayout;
    private LinearLayout mSelectLayout;
    private LinearLayout mControlLayout;
    private LinearLayout mActionLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainLayout = (LinearLayout) findViewById(R.id.main_field);
        mNextLayout = (LinearLayout) findViewById(R.id.next_field);
        mSelectLayout = (LinearLayout) findViewById(R.id.select_field);
        mControlLayout = (LinearLayout) findViewById(R.id.controller_field);
        mActionLayout = (LinearLayout) findViewById(R.id.action_field);

        initButton();
        initPuyoField(mMainField);
        initPuyoField(mNextField);
        setFieldLayout(mMainLayout, mMainField);
        setFieldLayout(mNextLayout, mNextField);
        setSelectField();

        // 連鎖取得までビュー非表示
        mControlLayout.setVisibility(View.GONE);
        // TODO: クリア条件設定作成
        mClearCondition = new ClearCondition(ClearCondition.Condition.ALL, 0);
    }

    /**
     * 配列をレイアウトに反映
     *
     * @param linearLayout レイアウト
     * @param field        配列
     */
    private void setFieldLayout(final LinearLayout linearLayout, final Puyo[][] field) {
        final int padding = 5;

        linearLayout.removeAllViews();
        for (int y = 0; y < field[0].length; y++) {
            final LinearLayout layout = new LinearLayout(this);
            for (int x = 0; x < field.length; x++) {
                final int subX = x;
                final int subY = y;
                final ImageView imageView = new ImageView(this);

                imageView.setImageResource(field[x][y].getDrawable());
                imageView.setBackgroundResource(R.drawable.background);
                imageView.setPadding(padding, padding, padding, padding);
                imageView.setOnClickListener((View view) -> {
                    field[subX][subY] = mCheckedPuyo;
                    imageView.setImageResource(mCheckedPuyo.getDrawable());
                });
                layout.addView(imageView);
            }
            linearLayout.addView(layout);
        }
    }

    /**
     * ぷよの色選択画面を設定
     */
    private void setSelectField() {
        final int divideNum = 2;

        for (int divide = 0; divide < divideNum; divide++) {
            final LinearLayout linearLayout = new LinearLayout(this);
            for (int index = Puyo.values().length / divideNum * divide; index < Puyo.values().length / divideNum * (divide + 1); index++) {
                final ImageButton button = new ImageButton(this);
                final Puyo puyo = Puyo.values()[index];

                button.setImageResource(puyo.getDrawable());
                button.setOnClickListener((View view) -> {
                    mCheckedPuyo = puyo;
                    for (int i = 0; i < mSelectLayout.getChildCount(); i++) {
                        final LinearLayout layout = (LinearLayout) mSelectLayout.getChildAt(i);
                        for (int j = 0; j < layout.getChildCount(); j++) {
                            layout.getChildAt(j).setEnabled(true);
                        }
                    }
                    button.setEnabled(false);
                });
                linearLayout.addView(button);
            }
            mSelectLayout.addView(linearLayout);
        }
    }

    /**
     * ボタン設定
     */
    private void initButton() {
        final ImageButton previousButton = (ImageButton) findViewById(R.id.controller_skip_previous);
        previousButton.setOnClickListener((View v) -> {
            if (mAnswer != null && mAnswerIndex > 0) {
                mAnswerIndex--;
                mMainField = convertField(mAnswer[mAnswerIndex]);
                setFieldLayout(mMainLayout, mMainField);
            }
        });

        final ImageButton nextButton = (ImageButton) findViewById(R.id.controller_skip_next);
        nextButton.setOnClickListener((View v) -> {
            if (mAnswer != null && mAnswerIndex < mAnswer.length - 1) {
                mAnswerIndex++;
                mMainField = convertField(mAnswer[mAnswerIndex]);
                setFieldLayout(mMainLayout, mMainField);
            }
        });

        final ImageButton returnButton = (ImageButton) findViewById(R.id.controller_return);
        returnButton.setOnClickListener((View v) -> {
            // フィールドを最初の状態に戻す
            mMainField = convertField(mAnswer[0]);
            setFieldLayout(mMainLayout, mMainField);

            mAnswer = null;
            mAnswerIndex = 0;
            mControlLayout.setVisibility(View.GONE);
            mSelectLayout.setVisibility(View.VISIBLE);
            mActionLayout.setVisibility(View.VISIBLE);
        });

        final ImageButton getButton = (ImageButton) findViewById(R.id.controller_get);
        getButton.setOnClickListener((View v) -> {
            final DialogFragment deleteFragment = AlertDialogFragment.newInstance(MainActivity.this, R.string.dialog_get_chain_message, REQUEST_CODE_GET_CHAIN);
            deleteFragment.show(getSupportFragmentManager(), null);
        });

        final ImageButton deleteButton = (ImageButton) findViewById(R.id.controller_delete);
        deleteButton.setOnClickListener((View v) -> {
            final DialogFragment deleteFragment = AlertDialogFragment.newInstance(MainActivity.this, R.string.dialog_delete_message, REQUEST_CODE_DELETE);
            deleteFragment.show(getSupportFragmentManager(), null);
        });
    }

    @Override
    public void onPositiveClick(int requestCode) {
        switch (requestCode) {
            case REQUEST_CODE_GET_CHAIN:
                if (checkNextarray(mNextField)) {
                    final int[][] main = convertField(mMainField);
                    final int[] next = convertNext(convertField(mNextField));
                    mAnswer = new ChainCalculator(main, next, mClearCondition).getAnswer();

                    mSelectLayout.setVisibility(View.GONE);
                    mActionLayout.setVisibility(View.GONE);
                    mControlLayout.setVisibility(View.VISIBLE);
                }
                break;
            case REQUEST_CODE_DELETE:
                initPuyoField(mMainField);
                initPuyoField(mNextField);
                setFieldLayout(mMainLayout, mMainField);
                setFieldLayout(mNextLayout, mNextField);
                break;
            default:
                break;
        }
    }

    /**
     * ネクストが正しく格納されているかもチェック
     *
     * @param next ネクスト
     * @return 整形後のネクスト
     */
    private boolean checkNextarray(final Puyo[][] next) {
        // ネクストがあるか
        if (next[0][0] == Puyo.NONE || next[1][0] == Puyo.NONE) {
            Toast.makeText(this, R.string.toast_no_next, Toast.LENGTH_SHORT).show();
            return false;
        }

        boolean isSpace = false;
        for (int y = 0; y < next[0].length; y++) {
            for (int x = 0; x < next.length; x++) {
                switch (next[x][y]) {
                    case OJAMA:
                        Toast.makeText(this, R.string.toast_contain_ojama, Toast.LENGTH_SHORT).show();
                        return false;
                    case KATA:
                        Toast.makeText(this, R.string.toast_contain_kata, Toast.LENGTH_SHORT).show();
                        return false;
                }

                if (!isSpace && next[x][y] == Puyo.NONE) {
                    isSpace = true;
                }

                // 空白の後にぷよが入っていないか
                if (isSpace && next[x][y] != Puyo.NONE) {
                    Toast.makeText(this, R.string.toast_divide_puyo, Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            // 片方だけ空白になっていないか
            if ((next[0][y] == Puyo.NONE) != (next[1][y] == Puyo.NONE)) {
                Toast.makeText(this, R.string.toast_one_side_puyo, Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    /**
     * ぷよ配列を初期化
     *
     * @param field ぷよ配列
     */
    private static void initPuyoField(final Puyo[][] field) {
        for (int x = 0; x < field.length; x++) {
            for (int y = 0; y < field[0].length; y++) {
                field[x][y] = Puyo.NONE;
            }
        }
    }

    /**
     * int配列をぷよ配列に変換
     *
     * @param field int配列
     * @return ぷよ配列
     */
    private static Puyo[][] convertField(final int[][] field) {
        final Puyo[][] array = new Puyo[field.length][field[0].length];

        for (int x = 0; x < array.length; x++) {
            for (int y = 0; y < array[0].length; y++) {
                for (Puyo puyo : Puyo.values()) {
                    if (field[x][y] == puyo.NUM) {
                        array[x][y] = puyo;
                        break;
                    }
                }
            }
        }
        return array;
    }

    /**
     * ぷよ配列をint配列に変換
     *
     * @param field ぷよ配列
     * @return int配列
     */
    private static int[][] convertField(final Puyo[][] field) {
        final int[][] array = new int[field.length][field[0].length];

        for (int x = 0; x < array.length; x++) {
            for (int y = 0; y < array[0].length; y++) {
                array[x][y] = field[x][y].NUM;
            }
        }
        return array;
    }

    /**
     * ネクストの空白を削除して１次元配列に変換
     *
     * @param next ネクスト配列
     * @return 整形後のネクスト配列
     */
    private static int[] convertNext(final int[][] next) {
        int count = next[0].length;
        for (int index = 0; index < next[0].length; index++) {
            if (next[0][index] == 0) {
                count = index;
                break;
            }
        }

        final int[] array = new int[count * 2];
        for (int y = 0; y < count; y++) {
            for (int x = 0; x < next.length; x++) {
                array[y * next.length + x] = next[x][y];
            }
        }
        return array;
    }

//    static {
//        System.loadLibrary("native-lib");
//    }
//    public native int[] getNode(final int[] main, final int[] next);
}
