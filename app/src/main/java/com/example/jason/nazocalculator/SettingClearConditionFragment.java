package com.example.jason.nazocalculator;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * クリア条件を設定するダイアログ
 */
public class SettingClearConditionFragment extends AppCompatDialogFragment {

    /**
     * ダイアログのクリックリスナー
     */
    interface OnClearConditionDialogClickListener {

        /**
         * ポジティブボタン押下時の動作
         *
         * @param conditionIndex    クリア条件のインデックス
         * @param conditionNumIndex 条件に関連する数のインデックス
         */
        void onClearConditionPositiveClick(final int conditionIndex, final int conditionNumIndex);
    }

    private static OnClearConditionDialogClickListener mListener;
    private Spinner mConditionSpinner;
    private Spinner mConditionNumSpinner;

    /**
     * {@link SettingClearConditionFragment}のインスタンス生成
     *
     * @param context {@link Context}
     * @return {@link SettingClearConditionFragment}のインスタンス
     */
    public static SettingClearConditionFragment newInstance(final Context context) {
        mListener = (OnClearConditionDialogClickListener) context;
        return new SettingClearConditionFragment();
    }


    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        // TODO : nullのExceptionをどうにかしたい
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_setting_clear_condition, null);
        init(view);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.dialog_title_clear_condition);
        builder.setView(view);
        builder.setPositiveButton(R.string.dialog_positive, (dialogInterface, i) -> {
            final int conditionIndex = mConditionSpinner.getSelectedItemPosition();
            final int conditionNumIndex = mConditionNumSpinner.getSelectedItemPosition();
            mListener.onClearConditionPositiveClick(conditionIndex, conditionNumIndex);
        });
        builder.setNegativeButton(R.string.dialog_negative, null);
        return builder.create();
    }

    /**
     * 初期設定
     */
    private void init(final View view) {
        final int MAX_CHAIN_NUM = 18;

        final ArrayAdapter<String> puyoAdapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item);
        for (Puyo puyo : Puyo.values()) {
            if (puyo != Puyo.NONE) {
                puyoAdapter.add(puyo.NAME);
            }
        }

        final ArrayAdapter<String> numAdapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item);
        for (int i = 1; i <= MAX_CHAIN_NUM; i++) {
            numAdapter.add(String.valueOf(i));
        }

        final ArrayAdapter<String> conditionAdapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item);
        for (ClearCondition.Condition condition : ClearCondition.Condition.values()) {
            conditionAdapter.add(condition.TITLE);
        }

        final TextView textView = (TextView) view.findViewById(R.id.text_clear_condition);
        mConditionNumSpinner = (Spinner) view.findViewById(R.id.spinner_clear_num);
        mConditionSpinner = (Spinner) view.findViewById(R.id.spinner_clear_condition);

        mConditionSpinner.setAdapter(conditionAdapter);
        mConditionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> adapterView, final View view, final int i, final long l) {
                final Spinner spinner = (Spinner) adapterView;
                final String title = (String) spinner.getSelectedItem();

                if (TextUtils.equals(title, ClearCondition.Condition.ALL.TITLE)) {
                    mConditionNumSpinner.setVisibility(View.GONE);
                    mConditionNumSpinner.setAdapter(null);
                    textView.setText(ClearCondition.Condition.ALL.MESSAGE);
                } else if (TextUtils.equals(title, ClearCondition.Condition.SOME_PUYO_ALL.TITLE)) {
                    mConditionNumSpinner.setVisibility(View.VISIBLE);
                    mConditionNumSpinner.setAdapter(puyoAdapter);
                    textView.setText(ClearCondition.Condition.SOME_PUYO_ALL.MESSAGE);
                } else if (TextUtils.equals(title, ClearCondition.Condition.CHAIN_NUM.TITLE)) {
                    mConditionNumSpinner.setVisibility(View.VISIBLE);
                    mConditionNumSpinner.setAdapter(numAdapter);
                    textView.setText(ClearCondition.Condition.CHAIN_NUM.MESSAGE);
                } else if (TextUtils.equals(title, ClearCondition.Condition.CHAIN_NUM_ALL.TITLE)) {
                    mConditionNumSpinner.setVisibility(View.VISIBLE);
                    mConditionNumSpinner.setAdapter(numAdapter);
                    textView.setText(ClearCondition.Condition.CHAIN_NUM_ALL.MESSAGE);
                }
            }

            @Override
            public void onNothingSelected(final AdapterView<?> adapterView) {
                // do nothing
            }
        });
    }
}
