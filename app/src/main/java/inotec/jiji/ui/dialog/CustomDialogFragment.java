package inotec.jiji.ui.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import inotec.jiji.R;

public class CustomDialogFragment extends DialogFragment {
    private View.OnClickListener listener;

    public static CustomDialogFragment newInstance(String title, String message) {
    	CustomDialogFragment fragment = new CustomDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("message", message);
        fragment.setArguments(args);

        return fragment;
    }

    public static CustomDialogFragment newInstance(String title, String message, boolean close_hide) {
    	CustomDialogFragment fragment = new CustomDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("message", message);
        args.putBoolean("close_hide", close_hide);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle safedInstanceState) {
    	String title = getArguments().getString("title");
    	String message = getArguments().getString("message");
    	boolean close_hide = getArguments().getBoolean("close_hide");

        Dialog dialog = new Dialog(getActivity());
        // タイトル非表示
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        // フルスクリーン
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.dialog_custom);
        // 背景を透明にする
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView titleText = (TextView)dialog.findViewById(R.id.title);
        TextView messageText = (TextView)dialog.findViewById(R.id.message);
        titleText.setText(title);
        messageText.setText(message);

        // OK ボタンのリスナ
        Button positiveButton = (Button)dialog.findViewById(R.id.positive_button);
        positiveButton.setOnClickListener(this.listener);
        // Close ボタンのリスナ
        Button closeButton = (Button)dialog.findViewById(R.id.close_button);
        closeButton.setOnClickListener(this.listener);
        if (close_hide) {
        	closeButton.setVisibility(View.GONE);
        }

        return dialog;
    }

    public void setCallbackListener(View.OnClickListener listener) {
        this.listener = listener;
    }
}