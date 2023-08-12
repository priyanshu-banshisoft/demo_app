package com.android.demo_app.app;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;


public abstract class BaseDialogFragment extends DialogFragment {
    public BaseDialogFragment()
    {
    }
    public static BaseDialogFragment getInstance(){
        return null;
    }


    public Context mContext;

    public String[] locationPermission = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    public String[] locationBackgroundPermission = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION};
    public String[] mediaPermissions = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
    public String[] docPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public String[] cameraPermissions = {Manifest.permission.CAMERA};

    public boolean isLocationPermissionEnable, isCameraPermissionEnable, isStoragePermissionEnable;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initViews(view, savedInstanceState);
        setupClickListener();
        loadData();
    }

    public abstract void initViews(View view, Bundle savedInstanceState);
    public abstract void setupClickListener();
    public abstract void loadData();

    public String getStringRes(@StringRes int resId)
    {
        if(getContext()!=null)
            return getResources().getString(resId);
        return "";
    }

    public String getStringRes(@StringRes int resId, Object... formatArgs)
    {
        if(getContext()!=null)
            return getResources().getString(resId, formatArgs);
        return "";
    }

    public void checkPermissionAuthorise() {
        isLocationPermissionEnable = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        isCameraPermissionEnable = ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;

        isStoragePermissionEnable = ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

}
