package inotec.jiji.ui;


import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import inotec.jiji.R;
import inotec.jiji.db.Word;
import inotec.jiji.db.WordDao;
import inotec.jiji.utils.Constants;

/**
 * 一覧表示アクティビティ
 */
public class WordListActivity extends Activity implements OnItemLongClickListener {
    // オブジェクト
	private TextView titleView = null;
    private Button ButtonRevert = null;
    private Button ButtonPrint = null;
	// 一覧表示用ListView
	private ListView listView = null;
    private Vibrator vibrator = null;

	private ArrayAdapter<Word> arrayAdapter = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 自動生成されたR.javaの定数を指定してXMLからレイアウトを生成
		setContentView(R.layout.activity_listprint);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

		titleView = (TextView) findViewById(R.id.title);
		titleView.setText("文章登録");
		// XMLで定義したandroid:idの値を指定してListViewを取得します。
		listView = (ListView) findViewById(R.id.list);

		// ListViewに表示する要素を保持するアダプタを生成します。
		arrayAdapter = new ArrayAdapter<Word>(this,
				android.R.layout.simple_list_item_1);

		// アダプタを設定
		listView.setAdapter(arrayAdapter);

		// リスナの追加
		listView.setOnItemLongClickListener(this);
        // 戻る
		ButtonRevert = (Button)findViewById( R.id.revert);
		ButtonRevert.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
                // バイブレーション
				if(vibrator.hasVibrator()) {
                    vibrator.vibrate(Constants.vibe_time);
                }
				// 前のアクティビティへ戻る
				finish();
				return false;
			}

		});
        // 新規
		ButtonPrint = (Button)findViewById( R.id.print);
		ButtonPrint.setText("新規");
		ButtonPrint.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
                // バイブレーション
				if(vibrator.hasVibrator()) {
                    vibrator.vibrate(Constants.vibe_time);
                }
				// 新規画面へ遷移
				Intent registIntent = new Intent(WordListActivity.this, WordRegistActivity.class);
				startActivity(registIntent);
				return false;
			}

		});
	}

	/**
	 * アクティビティが前面に来るたびにデータを更新
	 */
	@Override
	protected void onResume() {
		super.onResume();

		// データ取得タスクの実行
		DataLoadTask task = new DataLoadTask();
		task.execute();
	}

	/**
	 * 一覧データの取得と表示を行うタスク
	 */
	public class DataLoadTask extends AsyncTask<Object, Integer, List<Word>> {
		// 処理中ダイアログ
		private ProgressDialog progressDialog = null;

		@Override
		protected void onPreExecute() {
			// バックグラウンドの処理前にUIスレッドでダイアログ表示
			progressDialog = new ProgressDialog(WordListActivity.this);
			progressDialog.setMessage(getResources().getText(
					R.string.data_loading));
			progressDialog.setIndeterminate(true);
			progressDialog.show();
		}

		@Override
		protected List<Word> doInBackground(Object... params) {
			// 一覧データの取得をバックグラウンドで実行
			WordDao dao = new WordDao(WordListActivity.this);
			return dao.list();
		}

		@Override
		protected void onPostExecute(List<Word> result) {
			// 処理中ダイアログをクローズ
			progressDialog.dismiss();

			// 表示データのクリア
			arrayAdapter.clear();

			// 表示データの設定
			for (Word word : result) {
				arrayAdapter.add(word);
			}
		}
	}
	@Override
	/**
	 * List要素クリック時の処理
	 *
	 * 選択されたエンティティを詰めて参照画面へ遷移する
	 */
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
			long id) {
        // バイブレーション
		if(vibrator.hasVibrator()) {
            vibrator.vibrate(Constants.vibe_time);
        }
		// 選択された要素を取得する
		Word word = (Word) parent.getItemAtPosition(position);
		// 文章編集画面へ遷移
		Intent registIntent = new Intent(this, WordRegistActivity.class);
		registIntent.putExtra(Word.TABLE_NAME, word);
		startActivity(registIntent);
		return false;
	}
}
