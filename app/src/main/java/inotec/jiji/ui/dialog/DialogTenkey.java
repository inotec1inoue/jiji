package inotec.jiji.ui.dialog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.TextView;
import inotec.jiji.R;

/**
 * 一覧表示アクティビティ
 */
public class DialogTenkey extends Activity implements OnKeyListener {
	// 対象のオブジェクト
	// 一覧表示用ListView
	private TextView TextValue = null;
    private Button ButtonCancel = null;
    private Button ButtonInput = null;

	// 変数定義

	private final int OPE_CLR = 0;
	private final int OPE_00 = 1;

	private Button[] button_num_ = null;
	private Button[] button_ope_ = null;
	private TextView calc_result_ = null;
	private int value1_ = 0;
	private int value2_ = 0;
	private boolean bope_ = true;		//演算子入力直後
	private boolean berror_ = false;	//計算エラー
	private Long val = 0L;
	private String title_ = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 自動生成されたR.javaの定数を指定してXMLからレイアウトを生成
		setContentView(R.layout.tenkey);

        // XMLで定義したandroid:idの値を指定してListViewを取得します。
		TextValue = (TextView) findViewById(R.id.CalcResult);
		TextValue.setTextColor(Color.BLACK);

        // インテントからオブジェクトを取得
		Intent intent = getIntent();
		if (intent != null) {
			title_ = intent.getStringExtra("title");
			val = intent.getLongExtra("val", 0L);
			value1_ = val.intValue();
		}
		//タイトル
		if (title_ == null) {
			this.setTitle(getString(R.string.dialog_tenkey));
		}
		else {
			this.setTitle(title_);
		}
		//数値キー
		TextValue.setOnKeyListener(this);

		//数値ボタン
		button_num_ = new Button[10];
		button_num_[0] = (Button)findViewById(R.id.Button0);
		button_num_[1] = (Button)findViewById(R.id.Button1);
		button_num_[2] = (Button)findViewById(R.id.Button2);
		button_num_[3] = (Button)findViewById(R.id.Button3);
		button_num_[4] = (Button)findViewById(R.id.Button4);
		button_num_[5] = (Button)findViewById(R.id.Button5);
		button_num_[6] = (Button)findViewById(R.id.Button6);
		button_num_[7] = (Button)findViewById(R.id.Button7);
		button_num_[8] = (Button)findViewById(R.id.Button8);
		button_num_[9] = (Button)findViewById(R.id.Button9);
		for(int i=0; i<button_num_.length; i++) {
			button_num_[i].setOnClickListener(createClickListener_Number(i));
		}

		//演算子ボタン
		button_ope_ = new Button[2];
		button_ope_[0] = (Button)findViewById(R.id.Button_Clr);
		button_ope_[1] = (Button)findViewById(R.id.Button00);
		for(int i=0; i<button_ope_.length; i++) {
			button_ope_[i].setOnClickListener(createClickListener_Operator(i));
		}

		//結果画面
		calc_result_ = (TextView)findViewById(R.id.CalcResult);
		calc_result_.setText(val.toString());

		// 中止
		ButtonCancel = (Button)findViewById( R.id.cancel);
		ButtonCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// 前のアクティビティへ戻る
				finish();
			}

		});
        // 入力
		ButtonInput = (Button)findViewById( R.id.input);
		ButtonInput.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if(bope_) {
					val = (long)value1_;
				}
				else
				{
					val = (long)value2_;
				}
				Intent intent = new Intent();
				intent.putExtra("val", val);
				setResult(RESULT_OK, intent);
				finish();
			}

		});

	}
	///数値ボタン押下
	private void pushValue(int num) {
		value2_ = value2_ * 10 + num;
		calc_result_.setText("" + value2_);
		showResult(value2_, berror_);
		bope_ = false;
	}

	///演算子ボタン押下
	private void pushOperator(int ope) {
		if(ope == OPE_CLR) {
			value2_ = 0;
			bope_ = false;
			berror_ = false;
			showResult(0, berror_);
			return;
		}
		else if(ope == OPE_00) {
			value2_ = value2_ * 100;
			calc_result_.setText("" + value2_);
			showResult(value2_, berror_);
			bope_ = false;
			return;
		}
		if(!bope_) {
			value1_ = calculate(value1_, value2_);
			value2_ = 0;
			bope_ = true;
			showResult(value1_, berror_);
		}
	}

	///計算
	private int calculate(int val1, int val2) {
		val1 = val2;
		return val1;
	}

	///結果表示
	private void showResult(int val, boolean err) {
		if(!err) {
			calc_result_.setText("" + val);
		} else {
			calc_result_.setText("error");
		}
	}

	///数値キー押下イベント
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		boolean ret = false;
		if(event.getAction() == KeyEvent.ACTION_DOWN) {
			keyCode -= KeyEvent.KEYCODE_0;
			if(keyCode >= 0 && keyCode <= 9) {
				DialogTenkey.this.pushValue(keyCode);
				ret = true;
			}
		}
		return ret;
	}

	///数値ボタン押下イベント
	private OnClickListener createClickListener_Number(final int num) {
		return new OnClickListener() {
			public void onClick(View view) {
				DialogTenkey.this.pushValue(num);
			}
		};
	}

	///演算子ボタン押下イベント
	private OnClickListener createClickListener_Operator(final int ope) {
		return new OnClickListener() {
			public void onClick(View view) {
				DialogTenkey.this.pushOperator(ope);
			}
		};
	}
}
