package inotec.jiji.ui.dialog;

import java.util.ArrayList;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import inotec.jiji.R;

public class SingleDialogFragment extends DialogFragment implements OnItemClickListener {
    private View.OnClickListener listener;
    private ListView listView = null;
	private ArrayAdapter<String> arrayAdapter = null;
	public static int item = 0;

    public static SingleDialogFragment newInstance(String title, ArrayList<String> message, int pos) {
    	item = pos;	//2018.05.31 [追加]
    	SingleDialogFragment fragment = new SingleDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putStringArrayList("message", message);
        args.putInt("pos", pos);
        fragment.setArguments(args);

        return fragment;
    }

    public static SingleDialogFragment newInstance(String title, ArrayList<String> message, int pos, boolean close_hide) {
    	SingleDialogFragment fragment = new SingleDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putStringArrayList("message", message);
        args.putInt("pos", pos);
        args.putBoolean("close_hide", close_hide);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle safedInstanceState) {
    	String title = getArguments().getString("title");
    	ArrayList<String> message = getArguments().getStringArrayList("message");
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

		// ListViewに表示する要素を保持するアダプタを生成します。
		arrayAdapter = new ArrayAdapter<String>(this.getActivity(),
				android.R.layout.simple_list_item_single_choice)/* {
		   @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView)super.getView(position, convertView, parent);
                view.setTextSize( getResources().getDimension(R.dimen.text_size_medium) );
                return view;
            }
		}*/;
		// 表示データの設定
		arrayAdapter.clear();
		for (String item : message) {
			arrayAdapter.add(item);
		}
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