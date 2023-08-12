package com.android.demo_app.view_model;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;

import com.android.demo_app.app.BaseViewModel;


public class MainActivityViewModel extends BaseViewModel {

    public Uri fileUri;
    public String filePath;

    public MutableLiveData<Boolean> isUpdateEditPromiseDataLiveData = new MutableLiveData<>(false);

    public Uri getFileUri() {
        return fileUri;
    }

    public void setFileUri(Uri fileUri) {
        this.fileUri = fileUri;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void onDestroy() {

    }
}