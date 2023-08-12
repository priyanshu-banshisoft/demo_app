package com.android.demo_app.helper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;

public class KeyBoardHolder extends androidx.appcompat.widget.AppCompatEditText {
    public KeyBoardHolder(Context context) {
        super(context);
    }

    public KeyBoardHolder(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KeyBoardHolder(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
    }
}