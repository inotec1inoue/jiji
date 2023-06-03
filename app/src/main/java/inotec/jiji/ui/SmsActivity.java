package inotec.jiji.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import inotec.jiji.R;
import inotec.jiji.db.Contact;
import inotec.jiji.db.DatabaseOpenHelper;
import inotec.jiji.db.Dengon;
import inotec.jiji.db.DengonDao;
import inotec.jiji.db.Word;
import inotec.jiji.db.WordDao;
import inotec.jiji.utils.Constants;

/**
 * 参照アクティビティ
 */
public class SmsActivity extends Activity implements OnLongClickListener {

    // オブジェクト
	private Contact contact = new Contact();
    private Word word = null;
	private Dengon dengon = null;
    // 変数定義
    private Integer word_jun = 0;
    private Integer word_max = 0;
	private List<Word> WordList = new ArrayList<Word>();
    private Vibrator vibrator = null;

    // UI部品
    private Button ButtonSend = null;
    private Button ButtonCancel = null;
    private TextView simeiText = null;
    //private TextView telText = null;
    private TextView word_nameText = null;
    private Button ButtonList = null;
    private Button ButtonPrev = null;
    private Button ButtonNext = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 自動生成されたR.javaの定数を指定してXMLからレイアウトを生成
        setContentView(R.layout.activity_smsshow);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        // Intentから対象のContactオブジェクトを取得
        //contact = (Contact)getIntent().getSerializableExtra( Contact.TABLE_NAME);
        contact.setId(getIntent().getStringExtra( "id"));
        contact.setDial(getIntent().getStringExtra( "dial"));
        contact.setSimei(getIntent().getStringExtra( "simei"));
        contact.setKana(getIntent().getStringExtra( "kana"));

        // UI部品の取得
		simeiText = (TextView)findViewById( R.id.simeiLabel);
		//telText = (TextView)findViewById( R.id.telLabel);
		word_nameText = (TextView)findViewById( R.id.word_nameText);

        simeiText.setText( contact.getSimei());
        //telText.setText( contact.getDial());

        // 伝言を取得する
        DengonDao dengondao = new DengonDao(this);
        dengon = dengondao.load(Long.parseLong(contact.getId()));
        dengondao = null;
    	// SMS一覧を取得する
        WordDao worddao = new WordDao(this);
        WordList = worddao.list();
        word = worddao.clearWord();
        worddao = null;
        word_max = WordList.size();
        // 伝言を追加
        word.setWord_id(word_max.longValue() + 1L);
        word.setWord_name(dengon.getDengon_name());
        WordList.add(word);
        word_max = WordList.size();
        word_jun = word_max;
    	// 現在のSMSメモを取得する
        word = WordList.get(word_jun - 1);
        // 表示内容の更新
        updateView();

		// リスナの追加
		word_nameText.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
                // バイブレーション
				if(vibrator.hasVibrator()) {
                    vibrator.vibrate(Constants.vibe_time);
                }
				// SMS編集画面へ遷移
				Intent registIntent = new Intent(SmsActivity.this, SmsRegistActivity.class);
				registIntent.putExtra(Word.TABLE_NAME, word);
				startActivity(registIntent);
				return false;
			}

		});
        // やめる
		ButtonCancel = (Button)findViewById( R.id.cancel);
		ButtonCancel.setOnLongClickListener(new View.OnLongClickListener() {

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
        // おくる
		ButtonSend = (Button)findViewById( R.id.send);
		ButtonSend.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
                // バイブレーション
				if(vibrator.hasVibrator()) {
                    vibrator.vibrate(Constants.vibe_time);
                }
				// 伝言登録
		        DengonDao dengondao = new DengonDao(SmsActivity.this);
		        dengon = dengondao.clearDengon();
		        dengon.setContact_id(Long.parseLong(contact.getId()));
		        dengon.setDengon_name(WordList.get(word_jun - 1).getWord_name());
		        dengondao = null;
		        DengonSave(dengon);
		        // SMS送信
	    		String number = contact.getDial().replace("-",  "");
	    		String msgtext = word_nameText.getText().toString();
	    		//sendSms(number, msgtext);

				// 保存時に終了し、前のアクティビティへ戻る
				setResult( RESULT_OK);
				finish();
				/*
				// 確認ダイアログの表示
				mDialog = CustomDialogFragment.newInstance(getString(R.string.title_confirm), getString(R.string.confirm_excute));
				mDialog.setCallbackListener(new View.OnClickListener() {
				    @Override
				    public void onClick(View v) {
				    	if (v.getId() == R.id.positive_button) {
							mDialog.show(getFragmentManager(), "dialog_fragment");
							// SMS送信
				    		String number = contact.getDial().replace("-",  "");
				    		String msgtext = word_nameText.getText().toString();
				    		sendSms(number, msgtext);

							// 保存時に終了し、前のアクティビティへ戻る
							setResult( RESULT_OK);
							finish();
				    	}
		            	mDialog.dismiss();
				    }
				});
				mDialog.show(getFragmentManager(), "dialog_fragment");
				*/
				return false;
			}

		});

		ButtonList = (Button)findViewById( R.id.ButtonList);
        ButtonPrev = (Button)findViewById( R.id.ButtonPrev);
        ButtonNext = (Button)findViewById( R.id.ButtonNext);
        // えらぶ
        ButtonList.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
                // バイブレーション
				if(vibrator.hasVibrator()) {
                    vibrator.vibrate(Constants.vibe_time);
                }
            	// 現在表示中のエンティティを詰めて編集画面へ
                Intent intent = null;
	            intent = new Intent( SmsActivity.this, WordSelectActivity.class);
	            startActivityForResult( intent, R.id.ButtonList);
	            return false;
			}
		});
        // まえ
        ButtonPrev.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
                // バイブレーション
				if(vibrator.hasVibrator()) {
                    vibrator.vibrate(Constants.vibe_time);
                }
				if(word_jun > 1)
				{
					word_jun--;
				}
				else
				{
					word_jun = word_max;
				}
		    	// 現在のSMSメモを取得する
		        word = WordList.get(word_jun - 1);
				// 表示内容の更新
		        updateView();
		        return false;
			}
		});
        // つぎ
        ButtonNext.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
                // バイブレーション
				if(vibrator.hasVibrator()) {
                    vibrator.vibrate(Constants.vibe_time);
                }
				if(word_jun < word_max)
				{
					word_jun++;
				}
				else
				{
					word_jun = 1;
				}
		    	// 現在のSMSメモを取得する
		        word = WordList.get(word_jun - 1);
				// 表示内容の更新
		        updateView();
		        return false;
			}
		});
    }

    /**
     * 画面の表示内容を更新する
     */
    private void updateView(){
        word_nameText.setText( word.getWord_name());

    }
    /**
     * 編集画面の結果に応じて画面をリフレッシュ
     */
    @Override
  	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {
			case R.id.ButtonList:
				// 一覧
				if (resultCode == RESULT_OK) {
					Integer wk_word_jun = (Integer) data.getSerializableExtra("pos") + 1;
					word_jun = wk_word_jun;
			    	// 現在のルート位置を更新する
					word = WordList.get(word_jun - 1);
					// 表示を更新
					updateView();
				}
				break;
			case R.id.word_nameText:
				// 表示を更新
				updateView();
				break;
			default:
		}
    }


	@Override
	public boolean onLongClick(View v) {
		return false;
	}

	public void sendSms1(String number, String msgtext){
		Intent intent = new Intent(Intent.ACTION_SENDTO);
		//電話番号の指定
		Uri smsNumber = Uri.parse("word:" + number);
		intent.setData(smsNumber);
		//本文の指定
		intent.putExtra("sms_body", msgtext);
		//Activityの起動
		startActivity(Intent.createChooser(intent, "Pick a SMS App"));
	}
	public void sendSms(String number, String msgtext){
		String phoneNo = number.toString();
		String word = msgtext.toString();

		try {
			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(phoneNo, null, word, null, null);
			Toast.makeText(getApplicationContext(), "SMS Sent!",
					Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					"SMS faild, please try again later!",
					Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
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
}
