package inotec.jiji.ui;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import inotec.jiji.R;
import inotec.jiji.utils.Constants;

/**
 * 一覧表示アクティビティ
 */
public class HomeActivity extends Activity implements OnLongClickListener {
	private Button ButtonContact = null;
	private Button ButtonWord = null;
    private Vibrator vibrator = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 自動生成されたR.javaの定数を指定してXMLからレイアウトを生成
		setContentView(R.layout.activity_home);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        // フォルダが存在しなければ作成する
		String Path = Environment.getExternalStorageDirectory().getPath() + Constants.BASE_PATH;
	    File dirs = new File(Path);
	    if (!dirs.exists()) {
	        dirs.mkdirs();    //make folders
	    }
	    // フォルダが存在しなければ作成する
		Path = Environment.getExternalStorageDirectory().getPath() + "/CleanBak/";
	    dirs = new File(Path);
	    if (!dirs.exists()) {
	        dirs.mkdirs();    //make folders
	    }
        // 連絡先
	    ButtonContact = (Button)findViewById( R.id.home_contact);
	    ButtonContact.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
                // バイブレーション
				if(vibrator.hasVibrator()) {
                    vibrator.vibrate(Constants.vibe_time);
                }
				Intent intent = new Intent(HomeActivity.this, ContactShowActivity.class);
				startActivity(intent);
				return false;
			}

		});
        // 文章
		ButtonWord = (Button)findViewById( R.id.home_word);
		ButtonWord.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
                // バイブレーション
				if(vibrator.hasVibrator()) {
                    vibrator.vibrate(Constants.vibe_time);
                }
				Intent intent = new Intent(HomeActivity.this, WordListActivity.class);
				startActivity(intent);
				return false;
			}

		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(requestCode) {
		default:
		}
	}

	@Override
	public boolean onLongClick(View v) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}
}
