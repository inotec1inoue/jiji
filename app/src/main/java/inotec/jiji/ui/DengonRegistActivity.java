package inotec.jiji.ui;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import inotec.jiji.R;
import inotec.jiji.com.MyFilter;
import inotec.jiji.db.DatabaseOpenHelper;
import inotec.jiji.db.Dengon;
import inotec.jiji.db.DengonDao;
import inotec.jiji.db.Word;
import inotec.jiji.db.WordDao;
import inotec.jiji.ui.dialog.CustomDialogFragment;
import inotec.jiji.utils.Constants;

/**
 * 登録アクティビティ
 */
public class DengonRegistActivity extends Activity implements OnItemLongClickListener{
	// 対象のDengonオブジェクト
	private Dengon dengon = null;
    // 変数定義
	private InputFilter[] filters = { new MyFilter() };
    private Vibrator vibrator = null;
	// 一覧表示用ListView
	private ListView listView = null;
	private ArrayAdapter<Word> arrayAdapter = null;

	// UI部品
	private EditText dengon_nameText = null;
    private Button ButtonSave = null;
    private Button ButtonDelete = null;
    private Button ButtonCancel = null;
    private CustomDialogFragment mDialog = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wordregist);
		this.setTitle("伝言登録");
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		// UI部品の取得
		dengon_nameText = (EditText)findViewById( R.id.word_nameText);
		dengon_nameText.setHint("伝言を入力してください");
		dengon_nameText.setFilters(filters);
		// XMLで定義したandroid:idの値を指定してListViewを取得します。
		listView = (ListView) findViewById(R.id.list);

		// ListViewに表示する要素を保持するアダプタを生成します。
		arrayAdapter = new ArrayAdapter<Word>(this,
				android.R.layout.simple_list_item_1);

		// アダプタを設定
		listView.setAdapter(arrayAdapter);
		// リスナの追加
		listView.setOnItemLongClickListener(this);

		// 登録
		ButtonSave = (Button)findViewById( R.id.save);
		ButtonSave.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
                // バイブレーション
				if(vibrator.hasVibrator()) {
                    vibrator.vibrate(Constants.vibe_time);
                }
				if( dengon == null){
					dengon = new Dengon();
				}

				// 入力チェック
				final String dengon_name = dengon_nameText.getText().toString();

				// 確認ダイアログの表示
				mDialog = CustomDialogFragment.newInstance(getString(R.string.title_confirm), getString(R.string.confirm_save));
				mDialog.setCallbackListener(new View.OnClickListener() {
				    @Override
				    public void onClick(View v) {
				    	if (v.getId() == R.id.positive_button) {
							// Dengonオブジェクトに入力値を反映する
							dengon.setDengon_name( dengon_name);

							// 更新処理
							if( dengon_name.length() == 0){
								DengonDelete(dengon);
							} else {
								DengonSave(dengon);
							}

			    			// 保存時に終了し、前のアクティビティへ戻る
			                Intent intent = new Intent();
			    			intent.putExtra(Dengon.TABLE_NAME, dengon);
							// 保存時に終了し、前のアクティビティへ戻る
			    			setResult( RESULT_OK, intent);
							finish();
				    	}
		            	mDialog.dismiss();
				    }
				});
				mDialog.show(getFragmentManager(), "dialog_fragment");
				return false;
			}

		});
        // 削除
		ButtonDelete = (Button)findViewById( R.id.delete);
		ButtonDelete.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
                // バイブレーション
				if(vibrator.hasVibrator()) {
                    vibrator.vibrate(Constants.vibe_time);
                }
				// 確認ダイアログの表示
				mDialog = CustomDialogFragment.newInstance(getString(R.string.title_confirm), getString(R.string.confirm_delete));
				mDialog.setCallbackListener(new View.OnClickListener() {
				    @Override
				    public void onClick(View v) {
				    	if (v.getId() == R.id.positive_button) {
							// 削除処理
							DengonDao dao = new DengonDao( DengonRegistActivity.this);
							dao.delete( dengon);

							// 画面の更新
							clear();

							// 前のアクティビティへ戻る
							setResult( RESULT_OK);
							finish();
				    	}
		            	mDialog.dismiss();
				    }
				});
				mDialog.show(getFragmentManager(), "dialog_fragment");
				return false;
			}

		});
        // 中止
		ButtonCancel = (Button)findViewById( R.id.cancel);
		ButtonCancel.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
                // バイブレーション
				if(vibrator.hasVibrator()) {
                    vibrator.vibrate(Constants.vibe_time);
                }
				if (ChangeCheck()) {
					// 確認ダイアログの表示
					mDialog = CustomDialogFragment.newInstance(getString(R.string.title_confirm), getString(R.string.confirm_cancel));
					mDialog.setCallbackListener(new View.OnClickListener() {
					    @Override
					    public void onClick(View v) {
					    	if (v.getId() == R.id.positive_button) {
								finish();
					    	}
			            	mDialog.dismiss();
					    }
					});
					mDialog.show(getFragmentManager(), "dialog_fragment");
				} else {
					finish();
				}
				return false;
			}

		});

		// インテントからオブジェクトを取得
		Intent intent = getIntent();
		dengon = (Dengon)intent.getSerializableExtra( Dengon.TABLE_NAME);

		// UI部品に値を設定
		dengon_nameText.setText( dengon.getDengon_name());
		if( !dengon.getDengon_name().equals("")){
			// 削除機能は可能とする
			ButtonDelete.setVisibility(View.VISIBLE);
		} else {
			// 初めての入力なら削除機能は不可とする
			ButtonDelete.setVisibility(View.GONE);
		}
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
			progressDialog = new ProgressDialog(DengonRegistActivity.this);
			progressDialog.setMessage(getResources().getText(
					R.string.data_loading));
			progressDialog.setIndeterminate(true);
			progressDialog.show();
		}

		@Override
		protected List<Word> doInBackground(Object... params) {
			// 一覧データの取得をバックグラウンドで実行
			WordDao dao = new WordDao(DengonRegistActivity.this);
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
	/**
	 * List要素クリック時の処理
	 *
	 * 選択されたエンティティを詰めて参照画面へ遷移する
	 */
	/*
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// 選択された要素を取得する
		Word word = (Word) parent.getItemAtPosition(position);
		dengon_nameText.append(word.getWord_name());
	}
	*/

	/**
	 * 画面のクリア
	 */
	private void clear(){
		dengon = null;
		dengon_nameText.setText( null);
	}
	/**
	 * 変更チェック
	 */
	private boolean ChangeCheck(){
		boolean change_fg = false;

		if (!dengon_nameText.getText().toString().equals( dengon.getDengon_name())) {
			change_fg = true;
		}
		return change_fg;
	}

	// 伝言マスタの更新
	private void DengonSave(Dengon dengon) {
		String sql;
		Long count;
		DatabaseOpenHelper helper = new DatabaseOpenHelper(getBaseContext());
	    SQLiteStatement stmt = null;

		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor;

		db.beginTransaction();
		try {

			// tb_dengonの存在
			sql = "select count(*) from tb_dengon";
			sql += " where contact_id = " +  dengon.getContact_id().toString();
			cursor = db.rawQuery(sql, null);
			cursor.moveToFirst();
			if (!cursor.isAfterLast())
			{
				count = cursor.getLong(0);
			}
			else
			{
				count = 0L;
			}
			if (count == 0L) {
				stmt = db.compileStatement("insert into tb_dengon values (?,?);");
				stmt.bindLong(1, dengon.getContact_id());
				stmt.bindString(2, dengon.getDengon_name());

				stmt.executeInsert();
			}
			else
			{
	    		stmt = db.compileStatement("update tb_dengon set dengon_name = ? where contact_id = ?;");
	    		stmt.bindString(1, dengon.getDengon_name());
	    		stmt.bindLong(2, dengon.getContact_id());

				stmt.executeInsert();
			}

		    db.setTransactionSuccessful();
		} finally {
		    db.endTransaction();
		}

	}
	// 伝言マスタの削除
	private void DengonDelete(Dengon dengon) {
		String sql;
		Long count;
		DatabaseOpenHelper helper = new DatabaseOpenHelper(getBaseContext());
	    SQLiteStatement stmt = null;

		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor;

		db.beginTransaction();
		try {

			// tb_dengonの存在
			sql = "select count(*) from tb_dengon";
			sql += " where contact_id = " +  dengon.getContact_id().toString();
			cursor = db.rawQuery(sql, null);
			cursor.moveToFirst();
			if (!cursor.isAfterLast())
			{
				count = cursor.getLong(0);
			}
			else
			{
				count = 0L;
			}
			if (count != 0L) {
	    		stmt = db.compileStatement("delete from tb_dengon where contact_id = ?;");
	    		stmt.bindLong(1, dengon.getContact_id());

				stmt.executeInsert();
			}

		    db.setTransactionSuccessful();
		} finally {
		    db.endTransaction();
		}

	}
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		// TODO 自動生成されたメソッド・スタブ
        // バイブレーション
		if(vibrator.hasVibrator()) {
            vibrator.vibrate(Constants.vibe_time);
        }
		// 選択された要素を取得する
		Word word = (Word) parent.getItemAtPosition(position);
		dengon_nameText.append(word.getWord_name());
		return false;
	}

}
