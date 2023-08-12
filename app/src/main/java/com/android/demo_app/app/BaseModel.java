package com.android.demo_app.app;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseModel<T> {
    private static final String TAG = "BaseModel";

    private final AppCompatActivity appCompatActivity;

    public BaseModel(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }

    public AppCompatActivity getAppCompatActivity() {
        return appCompatActivity;
    }

    public abstract void onDestroy();
}
