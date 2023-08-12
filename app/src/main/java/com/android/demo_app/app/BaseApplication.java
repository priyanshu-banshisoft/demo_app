package com.android.demo_app.app;

import android.content.Context;
import android.content.DialogInterface;

import androidx.multidex.MultiDexApplication;

import com.android.demo_app.R;
import com.android.demo_app.helper.CheckInternetConnection;
import com.android.demo_app.helper.PreferenceManger;

import com.google.firebase.FirebaseApp;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

public class BaseApplication extends MultiDexApplication {

    private static final String TAG = "BaseApplication";
    private static BaseApplication instance;
    private static PreferenceManger preferenceManger;
    private static BaseApis baseApis;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        FirebaseApp.initializeApp(this);
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setFontAttrId(io.github.inflationx.calligraphy3.R.attr.fontPath)
                                .build())).build());

    }


    public static BaseApplication getInstance() {
        if (instance == null)
            instance = new BaseApplication();
        return instance;
    }

    public static BaseApis baseApisInstance() {
        if (baseApis == null)
            baseApis = new BaseApis();
        return baseApis;
    }

    public static PreferenceManger getPreferenceManger() {
        if (preferenceManger == null && getInstance() != null) {

            preferenceManger = new PreferenceManger(getInstance().getSharedPreferences(PreferenceManger.PREF_KEY, Context.MODE_PRIVATE));
        }
        return preferenceManger;
    }


    public boolean isInternetConnected(Context context) {
        if (new CheckInternetConnection(this).isConnected())
            return true;
        else {
            if (!Constants.isNetworkDialogOpened) {
                Constants.isNetworkDialogOpened = true;
                ((BaseActivity) context).hideLoader();
                ((BaseActivity) context).showAlertDialog("Network Error!!", "Please enable data or wifi connection", "OK", "", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Constants.isNetworkDialogOpened = false;
                    }
                });
            }
        }
        return false;
    }

}
