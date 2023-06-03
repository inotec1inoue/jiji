package inotec.jiji.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.InputFilter;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import inotec.jiji.R;
import inotec.jiji.com.MyFilter;
import inotec.jiji.db.Word;
import inotec.jiji.db.WordDao;
import inotec.jiji.ui.dialog.CustomDialogFragment;
import inotec.jiji.utils.Constants;

/**
 * 登録アクティビティ
 */
public class WordRegistActivity extends Activity implements OnLongClickListener {
	// 対象のWordオブジェクト
	private Word word = null;
    // 変数定義
	private InputFilter[] filters = { new MyFilter() };
    private Vibrator vibrator = null;

	// UI部品
	private EditText word_nameText = null;
    private Button ButtonSave = null;
    private Button ButtonDelete = null;	//2018.08.16 [追加]
    private Button ButtonCancel = null;
    private CustomDialogFragment mDialog = null;
    private CustomDialogFragment mDialog_msg = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wordregist);
		this.setTitle("文章登録");
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		// UI部品の取得
		word_nameText = (EditText)findViewById( R.id.word_nameText);
		word_nameText.setHint("文章を入力してください");
		word_nameText.setFilters(filters);
        // 登録
		ButtonSave = (Button)findViewById( R.id.save);
		ButtonSave.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
                // バイブレーション
				if(vibrator.hasVibrator()) {
                    vibrator.vibrate(Constants.vibe_time);
                }
				if( word == null){
					word = new Word();
				}

				// 入力チェック
				final String word_name = word_nameText.getText().toString();
				if( word_name.length() == 0){
			        mDialog_msg = CustomDialogFragment.newInstance(getString(R.string.title_confirm), getString(R.string.error_required), true);
					mDialog_msg.setCallbackListener(new View.OnClickListener() {
					    @Override
					    public void onClick(View v) {
			            	mDialog_msg.dismiss();
					    }
					});
					mDialog_msg.show(getFragmentManager(), "dialog_fragment");
					return false;
				}
				// 確認ダイアログの表示
				mDialog = CustomDialogFragment.newInstance(getString(R.string.title_confirm), getString(R.string.confirm_save));
				mDialog.setCallbackListener(new View.OnClickListener() {
				    @Override
				    public void onClick(View v) {
				    	if (v.getId() == R.id.positive_button) {
							// Wordオブジェクトに入力値を反映する
							word.setWord_name( word_name);

							// 更新処理
							WordDao dao = new WordDao( WordRegistActivity.this);
							word = dao.save( word);

							// 保存時に終了し、前のアクティビティへ戻る
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
							WordDao dao = new WordDao( WordRegistActivity.this);
							dao.delete( word);

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
		word = (Word)intent.getSerializableExtra( Word.TABLE_NAME);

		if( word != null){
			// 削除機能は可能とする
			ButtonDelete.setVisibility(View.VISIBLE);
		} else {
			WordDao dao = new WordDao(this);
			word = dao.clearWord();
			dao = null;
			// 初めての入力なら削除機能は不可とする
			ButtonDelete.setVisibility(View.GONE);
		}
		// UI部品に値を設定
		word_nameText.setText( word.getWord_name());
	}

	/**
	 * 画面のクリア
	 */
	private void clear(){
		word = null;
		word_nameText.setText( null);
	}
	/**
	 * 変更チェック
	 */
	private boolean ChangeCheck(){
		boolean change_fg = false;

		if (!word_nameText.getText().toString().equals( word.getWord_name())) {
			change_fg = true;
		}
		return change_fg;
	}

	@Override
	public boolean onLongClick(View v) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

}
