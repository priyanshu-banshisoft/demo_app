package com.android.demo_app.app;

import android.content.Context;
import android.net.Uri;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.android.demo_app.api.response.ApiResponse;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class BaseApis {

    BaseApis baseApis;
    Context context;

    public BaseApis() {
    }

    public BaseApis(Context context, BaseApis baseApis) {
        this.baseApis = baseApis;
        this.context = context;
    }

    public interface ApiResponseCallBack {
        void onSuccess(ApiResponse apiResponse);
        default void onFailed(){};
    }



}
