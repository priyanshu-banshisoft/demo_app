package com.android.demo_app.helper;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.brring.android.utils.StringHelper;
import com.brring.android.utils.StringUtill;

import java.text.NumberFormat;

public class CurrencyEditText extends PrefixEditText {

    private String current = "";
    private int totalLength = 0;
    private CurrencyEditText editText = CurrencyEditText.this;

    //properties
    private String Currency = "";
    private String Separator = ",";
    private Boolean Spacing = false;
    private Boolean Delimiter = false;
    private Boolean Decimals = true;
    private boolean backSpace = false;
    private OnTextChangedCallBack callBack;

    public CurrencyEditText(Context context) {
        super(context);
        init();
    }

    public CurrencyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CurrencyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {

        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.toString().equals(".00") || s.toString().equals(".0") || s.toString().equals(".")) s = "";
                String typedStr = s.toString();
                if (!StringHelper.isEmpty(s.toString())) {
                    if (!s.toString().equals(current)) {
                        String cleanString = "";
                        String[] splitStr = s.toString().split("\\.");
                        if (splitStr.length > 1) {
                            if (splitStr[1].length() > 2) {
                                double val = Double.parseDouble(splitStr[0]) + Double.parseDouble(StringUtill.formatDouble(Double.parseDouble(splitStr[1])));
                                typedStr = String.valueOf(s = String.valueOf(StringUtill.formatDouble(val)));
                                setText(typedStr);
                                setSelection(typedStr.length());
                            } else {
                                s = StringUtill.setCurrencyFormatVal(Double.parseDouble(s.toString().replace(".00", "").replace(getTag().toString(), "").replace("$", "").replace(".0", "").replace(",", "")));
                                editText.removeTextChangedListener(this);
                                s = s.toString().replace(".00", "")/*.replace(".0","")*/;
                            }
                        } else {
                            s = StringUtill.setCurrencyFormatVal(Double.parseDouble(s.toString().replace(".00", "").replace(getTag().toString(), "").replace("$", "").replace(".0", "").replace(",", "")));
                            editText.removeTextChangedListener(this);
                            s = s.toString().replace(".00", "")/*.replace(".0","")*/;
                        }
                        cleanString = s.toString().replaceAll("[$,.]", "").replaceAll(Currency, "").replaceAll("\\s+", "");
                        String currencyFormat = "";

                        if (Spacing) {
                            if (Delimiter) {
                                currencyFormat = Currency + ". ";
                            } else {
                                currencyFormat = Currency + " ";
                            }
                        } else {
                            if (Delimiter) {
                                currencyFormat = Currency + ".";
                            } else {
                                currencyFormat = Currency;
                            }
                        }

                        if (!backSpace) {
                            if (cleanString.length() != 0) {
                                try {
                                    double parsed;
                                    long parsedInt;
                                    String formatted = "";

                                    if (Decimals) {
                                        parsed = Double.parseDouble(cleanString);
                                        formatted = NumberFormat.getCurrencyInstance().format((parsed)).replace(NumberFormat.getCurrencyInstance().getCurrency().getSymbol(), currencyFormat);
//                                formatted = NumberFormat.getCurrencyInstance().format((parsed / 100)).replace(NumberFormat.getCurrencyInstance().getCurrency().getSymbol(), currencyFormat);
                                    } else {
                                        parsedInt = Long.parseLong(cleanString);
//                                formatted = currencyFormat + NumberFormat.getNumberInstance(Locale.US).format(parsedInt);
                                        formatted = currencyFormat + StringUtill.setCurrencyFormatVal(parsedInt);
                                    }

                                    int length  = 0;

                                    //if decimals are turned off and Separator is set as anything other than commas..
                                    if (totalLength == (cleanString.length() - 1)) {
                                        if (!Separator.equals(",") && !Decimals) {
                                            //..replace the commas with the new separator
                                            editText.setText(formatted.replaceAll(",", Separator));
                                            length = formatted.length();
                                        } else {
                                            formatted = typedStr;
                                            //since no custom separators were set, proceed with comma separation
                                            editText.setText(typedStr);
                                            length = typedStr.length();
                                        }
                                        editText.setSelection(length);
                                    }
                                    current = formatted;
                                    totalLength = cleanString.length();

                                    if (callBack != null)
                                        callBack.onTextChanged(formatted);
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (typedStr.equals("0")) {
                                if (callBack != null)
                                    callBack.onTextChanged("");
                            }
                            editText.addTextChangedListener(this);
                        }
                    }
                }
                else {
                    if (callBack != null)
                        callBack.onTextChanged(typedStr);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public interface OnTextChangedCallBack {
        void onTextChanged(String val);
    }

    public double getCleanDoubleValue() {
        double value = 0.0;
        if (Decimals) {
            value = Double.parseDouble(editText.getText().toString().trim().replaceAll("[$,]", "").replaceAll(Currency, ""));
        } else {
            String cleanString = editText.getText().toString().trim().replaceAll("[$,.]", "").replaceAll(Currency, "").replaceAll("\\s+", "");
            try {
                value = Double.parseDouble(cleanString);
            } catch (NumberFormatException e) {

            }
        }
        return value;
    }

    public int getCleanIntValue() {
        int value = 0;
        if (Decimals) {
            double doubleValue = Double.parseDouble(editText.getText().toString().trim().replaceAll("[$,]", "").replaceAll(Currency, ""));
            value = (int) Math.round(doubleValue);
        } else {
            String cleanString = editText.getText().toString().trim().replaceAll("[$,.]", "").replaceAll(Currency, "").replaceAll("\\s+", "");
            try {
                value = Integer.parseInt(cleanString);
            } catch (NumberFormatException e) {

            }
        }
        return value;
    }



    public void setDecimals(boolean value) {
        this.Decimals = value;
    }

    public void setCurrency(String currencySymbol) {
        this.Currency = currencySymbol;
    }

    public void setSpacing(boolean value) {
        this.Spacing = value;
    }

    public void setDelimiter(boolean value) {
        this.Delimiter = value;
    }

    public void setBackSpace(boolean value) {
        this.backSpace = value;
    }

    /**
     * Separator allows a custom symbol to be used as the thousand separator. Default is set as comma (e.g: 20,000)
     * <p>
     * Custom Separator cannot be set when Decimals is set as `true`. Set Decimals as `false` to continue setting up custom separator
     *
     * @value is the custom symbol sent in place of the default comma
     */
    public void setSeparator(String value) {
        this.Separator = value;
    }

    public void setTextChangedListener(OnTextChangedCallBack callBack) {
        this.callBack = callBack;
    }
}