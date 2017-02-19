package com.example.jason.nazocalculator;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;

/**
 * 注意を促すダイアログ
 */
public class AlertDialogFragment extends AppCompatDialogFragment {

    /**
     * ダイアログのクリックリスナー
     */
    interface OnDialogClickListener {

        /**
         * ポジティブボタン押下時の動作
         *
         * @param requestCode リクエストコード
         */
        void onPositiveClick(final int requestCode);
    }

    private static final String MESSAGE_KEY = "message_key";
    private static final String REQUEST_CODE_KEY = "request_code_key";
    private static OnDialogClickListener mListener;

    /**
     * {@link AlertDialogFragment}のインスタンス生成
     *
     * @param context     {@link Context}
     * @param message     表示するメッセージのリソースID
     * @param requestCode リクエストコード
     * @return {@link AlertDialogFragment}のインスタンス
     */
    static AlertDialogFragment newInstance(final Context context, final int message, final int requestCode) {
        mListener = (OnDialogClickListener) context;

        final AlertDialogFragment fragment = new AlertDialogFragment();
        final Bundle bundle = new Bundle();
        bundle.putInt(MESSAGE_KEY, message);
        bundle.putInt(REQUEST_CODE_KEY, requestCode);
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final int message = getArguments().getInt(MESSAGE_KEY);
        final int requestCode = getArguments().getInt(REQUEST_CODE_KEY);

        return new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton(R.string.dialog_positive, (DialogInterface dialogInterface, int value) -> mListener.onPositiveClick(requestCode))
                .setNegativeButton(R.string.dialog_negative, (DialogInterface dialogInterface, int value) -> {
                    // do nothing
                })
                .create();
    }
}
