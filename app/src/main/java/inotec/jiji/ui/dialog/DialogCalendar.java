package inotec.jiji.ui.dialog;

import java.text.MessageFormat;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import inotec.jiji.R;

/**
 * 一覧表示アクティビティ
 */
public class DialogCalendar extends Activity {
    // オブジェクト
	// 一覧表示用View
	private TextView titleView = null;
    private Button ButtonCancel = null;
    private Button ButtonOk = null;
    private Button ButtonPrev = null;
    private Button ButtonNext = null;
	private TextView[] list = null;
	private TextView[] daylist = null;
    // UI部品

    // 変数定義
	Calendar calendar = null;
	private int today_year = 0;
	private int today_month = 0;
	private int today_day = 0;
	private int year = 0;
	private int month = 0;
	private int day = 0;
	private int sel_year = 0;
	private int sel_month = 0;
	private int sel_day = 0;
	private int old_year = 0;
	private int old_month = 0;
	private int old_day = 0;
	private int dayOfWeek = 0;
	private int array = 0;
	private int old_index = 0;
	private String title_ = null;
	private String date_ = null;

    @Override
	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);

		// 自動生成されたR.javaの定数を指定してXMLからレイアウトを生成
		setContentView(R.layout.calendar);

		this.setTitle(getString(R.string.dialog_calender));

		calendar = Calendar.getInstance();
	    year = calendar.get(Calendar.YEAR);
	    month = calendar.get(Calendar.MONTH)+1;
	    day = calendar.get(Calendar.DAY_OF_MONTH);
	    dayOfWeek =calendar.get(Calendar.DAY_OF_WEEK);
	    today_year = year;
	    today_month = month;
	    today_day = day;

		titleView = (TextView) findViewById(R.id.title);
		titleView.setText(getString(R.string.dialog_calender));
        // インテントからオブジェクトを取得
		Intent intent = getIntent();
		if (intent != null) {
			title_ = intent.getStringExtra("title");
			date_ = intent.getStringExtra("date");
			if (title_ != null) {
				this.setTitle(title_);
			}
			if (date_ != null && !date_.equals(""))
			{
				sel_year = Integer.parseInt(date_.substring(0,4));
				sel_month = Integer.parseInt(date_.substring(4,6));
				sel_day = Integer.parseInt(date_.substring(6,8));
				old_year = sel_year;
				old_month = sel_month;
				old_day = sel_day;
				year = sel_year;
				month = sel_month;
				day = sel_day;
			}
		}

		// Cancel
		ButtonCancel = (Button)findViewById( R.id.cancel);
		ButtonCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// 前のアクティビティへ戻る
	            Intent intent = new Intent();
    			intent.putExtra("date", "");
    			setResult(RESULT_OK, intent);
				finish();
			}

		});
        // Ok
		ButtonOk = (Button)findViewById( R.id.ok);
		ButtonOk.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (sel_day != 0) {
		            Intent intent = new Intent();
		            // 書式を設定します。
		            MessageFormat mf = new MessageFormat("{0,number,0000}{1,number,00}{2,number,00}");
		            // オブジェクト配列を作成します。
		            Object[] objs={sel_year, sel_month, sel_day};
		            // フォーマットします。
		            date_ = mf.format(objs);

	    			intent.putExtra("date", date_);
	    			setResult(RESULT_OK, intent);
					finish();
				}
			}

		});
        // Prev
		ButtonPrev = (Button)findViewById( R.id.prev);
		ButtonPrev.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (month == 1) {
					year--;
					month = 12;
				} else {
					month--;
				}
				// カレンダーを表示
				CalendarDisp();
			}

		});
        // Next
		ButtonNext = (Button)findViewById( R.id.next);
		ButtonNext.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (month == 12) {
					year++;
					month = 1;
				} else {
					month++;
				}
				// カレンダーを表示
				CalendarDisp();
			}

		});

		list = new TextView[7];
		list[0] = (TextView)findViewById( R.id.TextSun);
		list[1] = (TextView)findViewById( R.id.TextMon);
		list[2] = (TextView)findViewById( R.id.TextThu);
		list[3] = (TextView)findViewById( R.id.TextWed);
		list[4] = (TextView)findViewById( R.id.TextTue);
		list[5] = (TextView)findViewById( R.id.TextFri);
		list[6] = (TextView)findViewById( R.id.TextSat);
		daylist = new TextView[42];
		daylist[0] = (TextView)findViewById( R.id.Text1);
		daylist[1] = (TextView)findViewById( R.id.Text2);
		daylist[2] = (TextView)findViewById( R.id.Text3);
		daylist[3] = (TextView)findViewById( R.id.Text4);
		daylist[4] = (TextView)findViewById( R.id.Text5);
		daylist[5] = (TextView)findViewById( R.id.Text6);
		daylist[6] = (TextView)findViewById( R.id.Text7);
		daylist[7] = (TextView)findViewById( R.id.Text8);
		daylist[8] = (TextView)findViewById( R.id.Text9);
		daylist[9] = (TextView)findViewById( R.id.Text10);
		daylist[10] = (TextView)findViewById( R.id.Text11);
		daylist[11] = (TextView)findViewById( R.id.Text12);
		daylist[12] = (TextView)findViewById( R.id.Text13);
		daylist[13] = (TextView)findViewById( R.id.Text14);
		daylist[14] = (TextView)findViewById( R.id.Text15);
		daylist[15] = (TextView)findViewById( R.id.Text16);
		daylist[16] = (TextView)findViewById( R.id.Text17);
		daylist[17] = (TextView)findViewById( R.id.Text18);
		daylist[18] = (TextView)findViewById( R.id.Text19);
		daylist[19] = (TextView)findViewById( R.id.Text20);
		daylist[20] = (TextView)findViewById( R.id.Text21);
		daylist[21] = (TextView)findViewById( R.id.Text22);
		daylist[22] = (TextView)findViewById( R.id.Text23);
		daylist[23] = (TextView)findViewById( R.id.Text24);
		daylist[24] = (TextView)findViewById( R.id.Text25);
		daylist[25] = (TextView)findViewById( R.id.Text26);
		daylist[26] = (TextView)findViewById( R.id.Text27);
		daylist[27] = (TextView)findViewById( R.id.Text28);
		daylist[28] = (TextView)findViewById( R.id.Text29);
		daylist[29] = (TextView)findViewById( R.id.Text30);
		daylist[30] = (TextView)findViewById( R.id.Text31);
		daylist[31] = (TextView)findViewById( R.id.Text32);
		daylist[32] = (TextView)findViewById( R.id.Text33);
		daylist[33] = (TextView)findViewById( R.id.Text34);
		daylist[34] = (TextView)findViewById( R.id.Text35);
		daylist[35] = (TextView)findViewById( R.id.Text36);
		daylist[36] = (TextView)findViewById( R.id.Text37);
		daylist[37] = (TextView)findViewById( R.id.Text38);
		daylist[38] = (TextView)findViewById( R.id.Text39);
		daylist[39] = (TextView)findViewById( R.id.Text40);
		daylist[40] = (TextView)findViewById( R.id.Text41);
		daylist[41] = (TextView)findViewById( R.id.Text42);

		for (array=0; array<42; array++) {
			final int index = array;
			daylist[array].setClickable(true);
			if ((array % 7) == 0) {
				daylist[array].setTextColor(Color.RED);
			}
			else if ((array % 7) == 6) {
				daylist[array].setTextColor(Color.BLUE);
			}
			daylist[array].setOnClickListener(new View.OnClickListener() {
			    @Override
			    public void onClick(View v) {
					if (daylist[index].getText().toString() != "") {
				        if ((old_year == today_year) && (old_month == today_month) && (old_day == today_day)) {
				    		daylist[old_index].setBackgroundDrawable(getResources().getDrawable(R.drawable.calendar_today));
				    		daylist[old_index].setTextColor(Color.WHITE);
				        } else {
				    		daylist[old_index].setBackgroundColor(Color.WHITE);
							if ((old_index % 7) == 0) {
								daylist[old_index].setTextColor(Color.RED);
							}
							else if ((old_index % 7) == 6) {
								daylist[old_index].setTextColor(Color.BLUE);
							} else {
					    		daylist[old_index].setTextColor(Color.BLACK);
							}
				        }
					    sel_year = year;
					    sel_month = month;
					    sel_day = Integer.parseInt(daylist[index].getText().toString());
					    old_index = index;
					    old_year = year;
					    old_month = month;
					    old_day = Integer.parseInt(daylist[index].getText().toString());

			    		daylist[index].setBackgroundDrawable(getResources().getDrawable(R.drawable.calendar_siage));
			    		daylist[index].setTextColor(Color.WHITE);
					}
			    }

			});
		}
		list[0].setText(getString(R.string.dow_sun));
        list[1].setText(getString(R.string.dow_mon));
        list[2].setText(getString(R.string.dow_tue));
        list[3].setText(getString(R.string.dow_wed));
        list[4].setText(getString(R.string.dow_thu));
        list[5].setText(getString(R.string.dow_fri));
        list[6].setText(getString(R.string.dow_sat));

        list[0].setTextColor(Color.RED);
        list[1].setTextColor(Color.BLACK);
        list[2].setTextColor(Color.BLACK);
        list[3].setTextColor(Color.BLACK);
        list[4].setTextColor(Color.BLACK);
        list[5].setTextColor(Color.BLACK);
        list[6].setTextColor(Color.BLUE);

		// カレンダーを表示
		CalendarDisp();

	}

    // カレンダー表示
    private void CalendarDisp() {

		titleView.setText(String.valueOf(year) + "年" + String.valueOf(month) + "月");

        for(int i=0;i<42;i++){
	        daylist[i].setText("");
    		daylist[i].setBackgroundColor(Color.WHITE);
			if ((i % 7) == 0) {
				daylist[i].setTextColor(Color.RED);
			}
			else if ((i % 7) == 6) {
				daylist[i].setTextColor(Color.BLUE);
			} else {
	    		daylist[i].setTextColor(Color.BLACK);
			}
        }
	    calendar.set(year,month-1,1);
        day=calendar.get(Calendar.DAY_OF_MONTH);
        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		//日1,月2,・・・土7
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        int lastDate = calendar.get(Calendar.DATE);
        for(int i=dayOfWeek;i<=dayOfWeek+lastDate-1;i++){
	        daylist[i-1].setText(String.valueOf(day));
	        if ((year == sel_year) && (month == sel_month) && (day == sel_day)) {
	    		daylist[i-1].setBackgroundDrawable(getResources().getDrawable(R.drawable.calendar_siage));
	    		daylist[i-1].setTextColor(Color.WHITE);
			    old_index = i-1;
	        } else if ((year == today_year) && (month == today_month) && (day == today_day)) {
	    		daylist[i-1].setBackgroundDrawable(getResources().getDrawable(R.drawable.calendar_today));
	    		daylist[i-1].setTextColor(Color.WHITE);
			}
	        day=day+1;
        }
    }
}