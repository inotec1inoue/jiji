package inotec.jiji.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.TextView;
import inotec.jiji.R;
import inotec.jiji.db.Contact;
import inotec.jiji.db.ContactDao;
import inotec.jiji.db.Dengon;
import inotec.jiji.db.DengonDao;
import inotec.jiji.db.Nokanri;
import inotec.jiji.db.NokanriDao;
import inotec.jiji.utils.Constants;

/**
 * 参照アクティビティ
 */
public class ContactShowActivity extends Activity implements OnLongClickListener {

    // 現在表示中のContactオブジェクト
	private List<Contact> ContactList = new ArrayList<Contact>();
    private Contact contact = new Contact();
    private Dengon dengon = null;
    private Nokanri nokanri = null;
    // 変数定義
    private Integer contact_jun = null;
    private Integer contact_max = null;
    private Vibrator vibrator = null;
    // UI部品
    private Handler mHandler = new Handler();
    private Runnable updateText;
    private TextView timeView = null;
	private TextView dayView = null;
    private TextView contact_idLabel = null;
    private TextView simeiLabel = null;
    private TextView kanaLabel = null;
    private TextView telLabel = null;
    private TextView dengonLabel = null;
    private Button Button01 = null;
    private Button Button02 = null;
    private Button ButtonPrev = null;
    private Button ButtonNext = null;
    private Button ButtonList = null;

	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        // 自動生成されたR.javaの定数を指定してXMLからレイアウトを生成
        setContentView(R.layout.activity_contactshow);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        // UI部品の取得
		timeView = (TextView) findViewById(R.id.time);
		timeView.setText(new SimpleDateFormat("HH:mm").format(new Date()));
		dayView = (TextView) findViewById(R.id.day);
		dayView.setText(new SimpleDateFormat("yyyy年M月d日(E)").format(new Date()));

		updateText = new Runnable() {
			public void run() {
				timeView.setText(new SimpleDateFormat("HH:mm").format(new Date()));
				dayView.setText(new SimpleDateFormat("yyyy年M月d日(E)").format(new Date()));
				mHandler.removeCallbacks(updateText);
				mHandler.postDelayed(updateText, 1000);
			}
		};
		mHandler.postDelayed(updateText, 1000);

		contact_idLabel = (TextView)findViewById( R.id.contact_idLabel);	//2012.12.10 [追加]
		simeiLabel = (TextView)findViewById( R.id.simeiLabel);
        kanaLabel = (TextView)findViewById( R.id.kanaLabel);
        telLabel = (TextView)findViewById( R.id.telLabel);
        dengonLabel = (TextView)findViewById( R.id.dengon_nameText);
        dengonLabel.setVisibility(View.INVISIBLE);
        /*
        dengonLabel.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
                // バイブレーション
				if(vibrator.hasVibrator()) {
                    vibrator.vibrate(Constants.vibe_time);
                }
				if (contact.getId() != null) {
					dengon.setContact_id(Long.parseLong(contact.getId()));
	            	// 伝言の編集画面へ
	                Intent intent = new Intent( ContactShowActivity.this, DengonRegistActivity.class);
	                intent.putExtra( Dengon.TABLE_NAME, dengon);
		            startActivityForResult( intent, R.id.dengon_nameText);
				}
				return false;
			}

		});
		*/
        Button01 = (Button)findViewById( R.id.Button01);
        Button02 = (Button)findViewById( R.id.Button02);
        ButtonPrev = (Button)findViewById( R.id.ButtonPrev);
        ButtonNext = (Button)findViewById( R.id.ButtonNext);
        ButtonList = (Button)findViewById( R.id.ButtonList);

        ContactDao contactdao = new ContactDao(this);
    	ContactList = contactdao.list();

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
    	// 現在の連絡先を取得する
		contact = ContactList.get(contact_jun - 1);
        // 表示内容の更新
        updateView();

        // でんわ
        Button01.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
                // バイブレーション
				if(vibrator.hasVibrator()) {
                    vibrator.vibrate(Constants.vibe_time);
                }
    			// 電話をかける
    			sendTel(contact.getDial());
    			return false;
			}

		});
        // でんごん
		Button02.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
                // バイブレーション
				if(vibrator.hasVibrator()) {
                    vibrator.vibrate(Constants.vibe_time);
                }
    			// メッセージを送る
    			sendSms(contact.getDial(), "");
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
				if(contact_jun > 1)
				{
					contact_jun--;
				}
				else
				{
					contact_jun = contact_max;
				}
				// 現在の連絡先を保存する
			    NokanriDao nokanridao = new NokanriDao(ContactShowActivity.this);
				nokanri.setItem_id("contact_jun");
				nokanri.setStart_no((long)contact_jun);
				nokanridao.Save(nokanri);
		    	nokanridao = null;
				// 現在の連絡先を取得する
		        contact = ContactList.get(contact_jun - 1);
		        // 表示内容の更新
		        updateView();
		        return false;
			}

		});
		//つぎ
		ButtonNext.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
                // バイブレーション
				if(vibrator.hasVibrator()) {
                    vibrator.vibrate(Constants.vibe_time);
                }
				if(contact_jun < contact_max)
				{
					contact_jun++;
				}
				else
				{
					contact_jun = 1;
				}
				// 現在の連絡先を保存する
			    NokanriDao nokanridao = new NokanriDao(ContactShowActivity.this);
				nokanri.setItem_id("contact_jun");
				nokanri.setStart_no((long)contact_jun);
				nokanridao.Save(nokanri);
		    	nokanridao = null;
		    	// 現在の連絡先を取得する
		        contact = ContactList.get(contact_jun - 1);
		        // 表示内容の更新
		        updateView();
		        return false;
			}

		});
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
	            intent = new Intent( ContactShowActivity.this, ContactSelectActivity.class);
	            startActivityForResult( intent, R.id.ButtonList);
	            return false;
			}

		});
    }

    /**
     * 画面の表示内容を更新する
     */
    private void updateView(){

    	contact_idLabel.setText( "");
		simeiLabel.setText( contact.getSimei());
        kanaLabel.setText( "");
        telLabel.setText( contact.getDial());
        // 伝言を取得する
        DengonDao dengondao = new DengonDao(this);
        dengon = dengondao.load(Long.parseLong(contact.getId()));
        dengondao = null;

        dengonLabel.setText( dengon.getDengon_name());
    }

    /**
     * 編集画面の結果に応じて画面をリフレッシュ
     */
    @Override
  	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {
			case R.id.Button01:
			case R.id.Button02:
				// 表示を更新
				updateView();
				break;
			case R.id.ButtonList:
				// えらぶ
				if (resultCode == RESULT_OK) {
					Integer wk_contact_jun = (Integer) data.getSerializableExtra("pos") + 1;
					contact_jun = wk_contact_jun;
					// 現在の連絡先を保存する
				    NokanriDao nokanridao = new NokanriDao(ContactShowActivity.this);
					nokanri.setItem_id("contact_jun");
					nokanri.setStart_no((long)contact_jun);
					nokanridao.Save(nokanri);
			    	nokanridao = null;
			    	// 現在のルート位置を更新する
					contact = ContactList.get(contact_jun - 1);
					// 表示を更新
					updateView();
				}
				break;
			case R.id.dengon_nameText:
				// 表示を更新
				updateView();
				break;
			default:
		}
    }

	public void sendSms(String number, String msgtext){
		/*
		Intent intent = new Intent(Intent.ACTION_SENDTO);
		//電話番号の指定
		Uri smsNumber = Uri.parse("sms:" + number);
		intent.setData(smsNumber);
		//本文の指定
		intent.putExtra("sms_body", msgtext);
		//Activityの起動
		startActivity(Intent.createChooser(intent, "Pick a SMS App"));
		*/
		// SMS画面へ
        Intent intent = new Intent( ContactShowActivity.this, SmsActivity.class);
        //intent.putExtra( Contact.TABLE_NAME, contact);
        intent.putExtra( "id", contact.getId());
        intent.putExtra( "dial", contact.getDial());
        intent.putExtra( "simei", contact.getSimei());
        intent.putExtra( "kana", contact.getKana());
        startActivity(intent);
	}
	public void sendTel(String number){
		Uri telNumber = Uri.parse("tel:" + number);
		Intent intent = new Intent(Intent.ACTION_DIAL, telNumber);
		startActivity(intent);
		/*
        Intent intent = new Intent( ContactShowActivity.this, DialogDial.class);
        intent.putExtra( "val", number);
		startActivity(intent);
		*/
	}
	private static boolean checkTelephoneNumberByRegularExpression(String number) {
		boolean result;
		result = checkTelephoneNumber(number, "^0\\d{1,4}-\\d{1,4}-\\d{4}$", "固定");
		if (result)	{
			return true;
		}
		/*
		result = checkTelephoneNumber(number, "^0\\d-\\d{4}-\\d{4}$", "固定");
		if (result)	{
			return true;
		}
		result = checkTelephoneNumber(number, "^\\(0\\d\\)\\d{4}-\\d{4}$", "固定");
		if (result)	{
			return true;
		}
		*/
		result = checkTelephoneNumber(number, "^(070|080|090)\\d{4}\\d{4}$", "携帯");
		if (result)	{
			return true;
		}
		result = checkTelephoneNumber(number, "^(070|080|090)-\\d{4}-\\d{4}$", "携帯");
		if (result)	{
			return true;
		}
		result = checkTelephoneNumber(number, "^050-\\d{4}-\\d{4}$", "IP");
		if (result)	{
			return true;
		}
		result = checkTelephoneNumber(number, "^0120-\\d{3}-\\d{3}$", "フリーダイアル");
		if (result)	{
			return true;
		}
		//System.out.println("不正な電話番号：" + number);
		return false;
	}
	private static boolean checkTelephoneNumber(String number, String regularExpression, String type) {
		Pattern pattern = Pattern.compile(regularExpression);
		Matcher matcher = pattern.matcher(number);
		if (matcher.find()) {
			//System.out.println(type + "電話番号：" + matcher.group());
			return true;
		}
		return false;
	}

	@Override
	public boolean onLongClick(View v) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

}
