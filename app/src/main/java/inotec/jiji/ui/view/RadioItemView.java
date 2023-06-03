package inotec.jiji.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import inotec.jiji.R;

public class RadioItemView extends FrameLayout implements Checkable {

    private RadioButton mRadioButton;

    public RadioItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    public RadioItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public RadioItemView(Context context) {
        super(context);
        initialize();
    }

    private void initialize() {
        // レイアウトを追加する
        addView(inflate(getContext(), R.layout.listview_radio, null));
        mRadioButton = (RadioButton) findViewById(R.id.radio_button);
    }

    @Override
    public boolean isChecked() {
        return mRadioButton.isChecked();
    }

    @Override
    public void setChecked(boolean checked) {
        // RadioButton の表示を切り替える
        mRadioButton.setChecked(checked);
		TextView textView1 = (TextView) findViewById(R.id.TextView01);
		textView1.setTextColor(checked ? Color.WHITE : Color.BLACK);
		TextView textView2 = (TextView) findViewById(R.id.TextView02);
		textView2.setTextColor(checked ? Color.WHITE : Color.BLACK);
		setBackgroundColor(checked ? Color.DKGRAY : Color.WHITE);
    }

    @Override
    public void toggle() {
    }

}