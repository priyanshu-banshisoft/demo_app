package com.android.demo_app.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

import com.android.demo_app.R;


public class StatusBarUtils {

    @RequiresApi(api = Build.VERSION_CODES.M)
    public  static  void statusBarColor(Activity context, int color){

        Window window = context.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(context.getResources().getColor(color));
    }

    public static void setLightStatusBar(Activity activity, int color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            View view = window.getDecorView();
            int flags = view.getSystemUiVisibility();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                flags = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;

            }
            view.setSystemUiVisibility(flags);
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            activity.getWindow().setStatusBarColor(activity.getResources().getColor(color));
        }
    }

    public static void setTransparentStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#00000000"));
        }
    }

    public static void removeTransparentStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            setLightStatusBar(activity, R.color.white);
        }
    }


    public  static  void setStatusBar(Activity context, String color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = context.getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);  // View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor(color));
        }
    }

    public static void SetStatusBarColor(Activity activity ,String statusBarColor, StatusBarState state)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(statusBarColor));
            int newUiVisibility = (int)window.getDecorView().getSystemUiVisibility();

            if (state == StatusBarState.Light)
            {
                //Dark Text to show up on your light status bar
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    newUiVisibility |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
            }
            else if (state == StatusBarState.Dark)
            {
                //Light Text to show up on your dark status bar
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    newUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
            }
            window.getDecorView().setSystemUiVisibility(newUiVisibility);
        }
    }

    public enum StatusBarState
    {
        Light,
        Dark
    }

}
