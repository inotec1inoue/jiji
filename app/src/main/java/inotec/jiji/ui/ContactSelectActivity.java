package inotec.jiji.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import inotec.jiji.R;
import inotec.jiji.db.Contact;
import inotec.jiji.db.ContactDao;
import inotec.jiji.db.Nokanri;
import inotec.jiji.db.NokanriDao;
import inotec.jiji.utils.Constants;

/**
 * 一覧表示アクティビティ
 */
public class ContactSelectActivity extends Activity implements OnLongClickListener {
	// オブジェクト
	private List<Contact> ContactList = new ArrayList<Contact>();
    private Nokanri nokanri = null;
	// 一覧表示用View
	private Button Button01 = null;
	private Button Button02 = null;
	private Button Button03 = null;
	private Button Button04 = null;
	private Button Button05 = null;
	private Button Button06 = null;
	private Button Button07 = null;
	//private Button Button08 = null;
    private Button ButtonCancel = null;
    private Button ButtonPrev = null;
    private Button ButtonNext = null;
	// UI部品

	// 変数定義
    private Integer contact_jun = null;
    private Integer contact_max = null;
    private Vibrator vibrator = null;
    private Integer mPage = 0;
    private static final Integer mItem = 7;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 自動生成されたR.javaの定数を指定してXMLからレイアウトを生成
		setContentView(R.layout.activity_wordselect);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

		ContactDao dao = new ContactDao(ContactSelectActivity.this);
		//アドレス情報の取得
		ContactList = dao.list();
		dao = null;
    	// 現在の連絡先を取得する
		NokanriDao nokanridao = new NokanriDao(this);
    	nokanri = nokanridao.load("contact_jun");
        nokanridao = null;
        contact_jun = nokanri.getStart_no().intValue();
        if ((contact_jun < 1)) {
        	contact_jun = 1;
        }
		// ルートの件数を取得する
		contact_max = ContactList.size();
		// 件数がルート順より少なかったら
		if (contact_max < contact_jun) {
			contact_jun = contact_max;
		}
		mPage = (int)((contact_jun - 1) / mItem);
		// 1
		Button01 = (Button) findViewById(R.id.Button01);
		Button01.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				if (ContactList.size() > mPage * mItem + 0) {
	                // バイブレーション
					if(vibrator.hasVibrator()) {
	                    vibrator.vibrate(Constants.vibe_time);
	                }
					Intent intent = new Intent();
					intent.putExtra("pos", mPage * mItem + 0);
					setResult(RESULT_OK, intent);
					finish();
				}
				return false;
			}

		});
		// 2
		Button02 = (Button) findViewById(R.id.Button02);
		Button02.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				if (ContactList.size() > mPage * mItem + 1) {
	                // バイブレーション
					if(vibrator.hasVibrator()) {
	                    vibrator.vibrate(Constants.vibe_time);
	                }
					Intent intent = new Intent();
					intent.putExtra("pos", mPage * mItem + 1);
					setResult(RESULT_OK, intent);
					finish();
				}
				return false;
			}

		});
		// 3
		Button03 = (Button) findViewById(R.id.Button03);
		Button03.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				if (ContactList.size() > mPage * mItem + 2) {
	                // バイブレーション
					if(vibrator.hasVibrator()) {
	                    vibrator.vibrate(Constants.vibe_time);
	                }
					Intent intent = new Intent();
					intent.putExtra("pos", mPage * mItem + 2);
					setResult(RESULT_OK, intent);
					finish();
				}
				return false;
			}

		});
		// 4
		Button04 = (Button) findViewById(R.id.Button04);
		Button04.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				if (ContactList.size() > mPage * mItem + 3) {
	                // バイブレーション
					if(vibrator.hasVibrator()) {
	                    vibrator.vibrate(Constants.vibe_time);
	                }
					Intent intent = new Intent();
					intent.putExtra("pos", mPage * mItem + 3);
					setResult(RESULT_OK, intent);
					finish();
				}
				return false;
			}

		});
		// 5
		Button05 = (Button) findViewById(R.id.Button05);
		Button05.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				if (ContactList.size() > mPage * mItem + 4) {
	                // バイブレーション
					if(vibrator.hasVibrator()) {
	                    vibrator.vibrate(Constants.vibe_time);
	                }
					Intent intent = new Intent();
					intent.putExtra("pos", mPage * mItem + 4);
					setResult(RESULT_OK, intent);
					finish();
				}
				return false;
			}

		});
		// 6
		Button06 = (Button) findViewById(R.id.Button06);
		Button06.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				if (ContactList.size() > mPage * mItem + 5) {
	                // バイブレーション
					if(vibrator.hasVibrator()) {
	                    vibrator.vibrate(Constants.vibe_time);
	                }
					Intent intent = new Intent();
					intent.putExtra("pos", mPage * mItem + 5);
					setResult(RESULT_OK, intent);
					finish();
				}
				return false;
			}

		});
		// 7
		Button07 = (Button) findViewById(R.id.Button07);
		Button07.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				if (ContactList.size() > mPage * mItem + 6) {
	                // バイブレーション
					if(vibrator.hasVibrator()) {
	                    vibrator.vibrate(Constants.vibe_time);
	                }
					Intent intent = new Intent();
					intent.putExtra("pos", mPage * mItem + 6);
					setResult(RESULT_OK, intent);
					finish();
				}
				return false;
			}

		});
		/*
		// 8
		Button08 = (Button) findViewById(R.id.Button08);
		Button08.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				if (ContactList.size() > mPage * mItem + 7) {
	                // バイブレーション
					if(vibrator.hasVibrator()) {
	                    vibrator.vibrate(Constants.vibe_time);
	                }
					Intent intent = new Intent();
					intent.putExtra("pos", mPage * mItem + 7);
					setResult(RESULT_OK, intent);
					finish();
				}
				return false;
			}

		});
		*/
        // やめる
		ButtonCancel = (Button)findViewById( R.id.cancel);
		ButtonCancel.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
                // バイブレーション
				if(vibrator.hasVibrator()) {
                    vibrator.vibrate(Constants.vibe_time);
                }
				finish();
				return false;
			}

		});
        // まえ
		ButtonPrev = (Button)findViewById( R.id.ButtonPrev);
        ButtonPrev.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
                // バイブレーション
				if(vibrator.hasVibrator()) {
                    vibrator.vibrate(Constants.vibe_time);
                }
				if (mPage > 0) {
					mPage--;
				} else {
					mPage = (int)(ContactList.size() / mItem);
				}
				// データ取得タスクの実行
				DataLoadTask task = new DataLoadTask();
				task.execute();
				return false;
			}
		});
        // つぎ
		ButtonNext = (Button)findViewById( R.id.ButtonNext);
        ButtonNext.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
                // バイブレーション
				if(vibrator.hasVibrator()) {
                    vibrator.vibrate(Constants.vibe_time);
                }
				if (mPage < (int)(ContactList.size() / mItem)) {
					mPage++;
				} else {
					mPage = 0;
				}
				// データ取得タスクの実行
				DataLoadTask task = new DataLoadTask();
				task.execute();
				return false;
			}
		});
	}
/*
	private LinearLayout.LayoutParams createParam(int w, int h) {
		return new LinearLayout.LayoutParams(w, h);
	}
*/
	/**
	 * 編集画面の結果に応じて画面をリフレッシュ
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
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
	public class DataLoadTask extends AsyncTask<Object, Integer, List<Contact>> {

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected List<Contact> doInBackground(Object... params) {
			ContactDao dao = new ContactDao(ContactSelectActivity.this);
			//アドレス情報の取得
			ContactList = dao.list();

			return ContactList;
		}

		@Override
		protected void onPostExecute(List<Contact> result) {
			if (ContactList.size() > mPage * mItem + 0) {
				Button01.setText(ContactList.get(mPage * mItem + 0).getSimei());
			} else {
				Button01.setText("");
			}
			if (ContactList.size() > mPage * mItem + 1) {
				Button02.setText(ContactList.get(mPage * mItem + 1).getSimei());
			} else {
				Button02.setText("");
			}
			if (ContactList.size() > mPage * mItem + 2) {
				Button03.setText(ContactList.get(mPage * mItem + 2).getSimei());
			} else {
				Button03.setText("");
			}
			if (ContactList.size() > mPage * mItem + 3) {
				Button04.setText(ContactList.get(mPage * mItem + 3).getSimei());
			} else {
				Button04.setText("");
			}
			if (ContactList.size() > mPage * mItem + 4) {
				Button05.setText(ContactList.get(mPage * mItem + 4).getSimei());
			} else {
				Button05.setText("");
			}
			if (ContactList.size() > mPage * mItem + 5) {
				Button06.setText(ContactList.get(mPage * mItem + 5).getSimei());
			} else {
				Button06.setText("");
			}
			if (ContactList.size() > mPage * mItem + 6) {
				Button07.setText(ContactList.get(mPage * mItem + 6).getSimei());
			} else {
				Button07.setText("");
			}
			/*
			if (ContactList.size() > mPage * mItem + 7) {
				Button08.setText(ContactList.get(mPage * mItem + 7).getSimei());
			} else {
				Button08.setText("");
			}
			*/
		}
	}

	@Override
	public boolean onLongClick(View v) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}
}
