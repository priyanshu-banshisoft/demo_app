package com.android.demo_app.app;

public class AppDelegate {
    public static AppDelegate ad;

    public static AppDelegate getInstance() {
        if (ad == null) {
            ad = new AppDelegate();
        }
        return ad;
    }

    public static void setInstance(AppDelegate instance) {
        AppDelegate.ad = instance;
    }


    public static AppDelegate getAd() {
        return ad;
    }

    public static void setAd(AppDelegate ad) {
        AppDelegate.ad = ad;
    }
}
