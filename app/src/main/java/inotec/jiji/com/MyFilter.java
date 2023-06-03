package inotec.jiji.com;

import android.text.InputFilter;
import android.text.Spanned;

public class MyFilter implements InputFilter {
    public CharSequence filter(CharSequence source, int start, int end,
                               Spanned dest, int dstart, int dend) {

        if( !source.toString().matches("\n") ){
            return source;
        }else{
            return "";
        }
    }
}