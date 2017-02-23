package com.example.jason.nazocalculator;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;

// TODO: まずはパッケージを分けてみてはどうでしょう。ルールはありませんが、UI関連のものと、ロジックは分けておいたりすると良い気がします。

/**
 * 注意を促すダイアログ
 */
public class AlertDialogFragment extends AppCompatDialogFragment {
    // TODO: 汎用的なダイアログっぽい名前なので、そこを意識するのであれば、タイトルとかボタンの文字列とかも変更できないと、いろいろな場面で使えない様に思います。
    // 逆に固定の用途でしか使わないなら、全体的にもっとその用途っぽい名前の付け方だったり、メッセージも固定で入れてしまったりすると良いと思います。

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
        // TODO: 汎用的なダイアログとして作るなら、いろんなシーンを考えて、全部のイベント通知しておくと良いと思います。
    }

    private static final String MESSAGE_KEY = "message_key";
    private static final String REQUEST_CODE_KEY = "request_code_key";
    // TODO: Fragmentのコールバックは、staticでやってはいけません。ActivityやTargetFragmentを使いましょう。
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
                .setNegativeButton(R.string.dialog_negative, null)
                .create();
    }
}
