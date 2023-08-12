package com.android.demo_app.helper;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.DecimalFormat;

//This class is for entering currency amount while keeping the right format as the user enters values
public class CurrencyTextWatcher implements TextWatcher {
    private final DecimalFormat dfnd;
    private final EditText et;
    OnTextWatcher textWatcher;

    public interface OnTextWatcher {
        default void beforeTextChanged(String s){}
        default void onTextChanged(String s){}
        default void afterTextChanged(String s){}
    }

    public CurrencyTextWatcher(EditText editText) {
        dfnd = new DecimalFormat("#");
        this.et = editText;
    }

    public CurrencyTextWatcher(EditText editText, OnTextWatcher textWatcher) {
        dfnd = new DecimalFormat("#");
        this.et = editText;
        this.textWatcher = textWatcher;
    }

    @Override
    public void afterTextChanged(Editable s) {
        et.removeTextChangedListener(this);
        //After all the text editing, if there is a string to validate - format it
        if (s != null && !s.toString().isEmpty()) {
            try {
                //Take the input string and remove all formatting characters
               String v = s.toString().replace("₹","").replace(" ","");
//                //Pass the altered string to a number
//                Number n = dfnd.parse(v);
//                //Get the decimal point correct again
//               // n = n.doubleValue() / 100.0;
//                //Reformat the text with currency symbols, grouping places etc.
//                et.setText(dfnd.format(n));
                //Add the Dollar symbol ($)
                et.setText("₹".concat(" ").concat(v));
                if (textWatcher != null)
                    textWatcher.afterTextChanged(et.getText().toString());
                //Move the editing cursor back to the right place
                et.setSelection(et.getText().length());

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } else
        {
            et.setText("");
        }

        et.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (textWatcher != null)
            textWatcher.beforeTextChanged(et.getText().toString());
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {
        if (textWatcher != null)
            textWatcher.onTextChanged(et.getText().toString());
    }
}