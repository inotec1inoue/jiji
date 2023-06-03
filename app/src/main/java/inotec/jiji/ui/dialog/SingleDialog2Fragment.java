package inotec.jiji.ui.dialog;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import inotec.jiji.R;

public class SingleDialog2Fragment extends DialogFragment implements OnItemClickListener {
    private View.OnClickListener listener;
    private ListView listView = null;
    private SimpleAdapter arrayAdapter = null;
	public static int item = 0;

    public static SingleDialog2Fragment newInstance(String title, ArrayList<HashMap<String, String>> message_list, int pos) {
    	item = pos;	//2018.05.31 [追加]
    	SingleDialog2Fragment fragment = new SingleDialog2Fragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putSerializable("message_list", message_list);
        args.putInt("pos", pos);
        fragment.setArguments(args);

        return fragment;
    }

    public static SingleDialog2Fragment newInstance(String title, ArrayList<HashMap<String, String>> message_list, int pos, boolean close_hide) {
    	SingleDialog2Fragment fragment = new SingleDialog2Fragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putSerializable("message_list", message_list);
        args.putInt("pos", pos);
        args.putBoolean("close_hide", close_hide);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle safedInstanceState) {
    	String title = getArguments().getString("title");
    	ArrayList<HashMap<String, String>> message_list = (ArrayList<HashMap<String, String>>)getArguments().getSerializable("message_list");
    	int pos = getArguments().getInt("pos");
    	boolean close_hide = getArguments().getBoolean("close_hide");

        Dialog dialog = new Dialog(getActivity());
        // タイトル非表示
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        // フルスクリーン
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.dialog_choice);
        // 背景を透明にする
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView titleText = (TextView)dialog.findViewById(R.id.title);
		listView = (ListView) dialog.findViewById(R.id.list);

		arrayAdapter = new SimpleAdapter(this.getActivity().getApplicationContext(),
				message_list, R.layout.radio_view_item,
				new String[] { "name", "value" }, new int[] { R.id.TextView01, R.id.TextView02 });
		// アダプタを設定
		listView.setAdapter(arrayAdapter);

		// 選択の方式の設定
	    listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		// 指定したアイテムがチェックされているかを設定
	    listView.setItemChecked(pos, true);
	    // 現在チェックされているアイテムの position を取得
	    listView.getCheckedItemPosition();

		// リスナの追加
		listView.setOnItemClickListener(this);

		titleText.setText(title);

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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		item = position;
	}
}