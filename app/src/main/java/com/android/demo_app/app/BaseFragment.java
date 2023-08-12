package com.android.demo_app.app;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.demo_app.helper.AppProgressBar;
import com.android.demo_app.helper.PreferenceManger;
import com.androidadvance.topsnackbar.TSnackbar;


import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class BaseFragment extends Fragment {

    public static final String BASE_URL = "";

    public Context mContext;

    public String SOMETHING_WENT_WRONG = "Something went wrong!";
    public String ERROR = "Error!";
    public String API_RESPONSE = "api response";
    public String APILOADINGTEXT = "Please wait...";
    public String NO_DATA_FOUND = "Nothing to show here yet!";
    public String SESSION_EXPIRED_TEXT = "Session expired,Please Login Again.";

    public PreferenceManger preferenceManger;

    public String[] locationPermission = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    public String[] mediaPermissions = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
    public String[] docPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public String[] cameraPermissions = {Manifest.permission.CAMERA};

    public boolean isLocationPermissionEnable, isBackgroundLocationPermission, isCameraPermissionEnable, isStoragePermissionEnable;

    public static boolean checkGPSStatus(Context context) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return statusOfGPS;
    }

    @SuppressWarnings("deprecation")
    public static boolean isLocationEnabled(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // This is a new method provided in API 28
            LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            return lm.isLocationEnabled();
        } else {
            // This was deprecated in API 28
            int mode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE,
                    Settings.Secure.LOCATION_MODE_OFF);
            return (mode != Settings.Secure.LOCATION_MODE_OFF);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            checkPermissionAuthorise();
        }
        //preferenceManger = BaseApplication.getPreferenceManger();
    }


    public void checkPermissionAuthorise() {
        isLocationPermissionEnable = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            isBackgroundLocationPermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED;
        }

        isCameraPermissionEnable = ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;

        isStoragePermissionEnable = ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean isLocationPermissionEnable() {
        return ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean isBackgroundLocationPermission() {
        return ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean isCameraPermissionEnable() {
        return ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean isStoragePermissionEnable() {
        return ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean isGPSEnabled(Context mContext) {
        LocationManager locationManager = (LocationManager)
                mContext.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public void showSnackBar(View view, String msg) {
        try {
            TSnackbar snackbar = TSnackbar.make(view,msg, TSnackbar.LENGTH_LONG);
            TextView textView = snackbar.getView().findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
           // snackbar.setActionTextColor(Color.WHITE);
            snackbar.show();
        } catch (Exception e) {
            Log.e(NO_DATA_FOUND, e.getMessage());
        }

    }

    public RequestBody toRequestBody(String value) {
        return !TextUtils.isEmpty(value) ? RequestBody.create(MediaType.parse("text/plain"), value) : null;
    }

    public String getFileNameFromUrl(String url) {
        if (TextUtils.isEmpty(url))
            return "";
        else
            return url.substring(url.lastIndexOf('/') + 1);
    }



    public void showLoader() {
        AppProgressBar.showLoader(mContext);
    }

    public void hideLoader() {
        AppProgressBar.hideLoaderDialog();
    }


    public String capitalizeFirstLetter(String str) {
        if (str == null) return "";
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }


    public double calculateAverage(ArrayList<Integer> list) {
        if (list == null || list.isEmpty()) {
            return 0;
        }

        double sum = 0;
        for (Integer mark : list) {
            sum += mark;
        }
        return sum / list.size();
    }

    public double calculateFloatAverage(ArrayList<Float> list) {
        if (list == null || list.isEmpty()) {
            return 0;
        }
        int count = 0;

        double sum = 0;
        for (Float mark : list) {
            if (Float.compare(mark, .1f) > 0) {
                count++;
                sum += mark;
            }
        }
        return sum / count;
    }

    public double calculateDoubleAverage(ArrayList<Double> list) {
        if (list == null || list.isEmpty()) {
            return 0;
        }

        double sum = 0;
        for (Double mark : list) {
            sum += mark;
        }
        return sum / list.size();
    }


    public String getSplitString(String txt, String regex, int pos) {
        if (TextUtils.isEmpty(txt))
            return "";
        String[] s = txt.split(regex);
        return s[pos];
    }


    public int getIntFromString(String value) {
        if (TextUtils.isEmpty(value))
            return 0;
        else return Integer.parseInt(value);
    }

    public String setStringValue(String value) {
        if (TextUtils.isEmpty(value))
            return "";
        else return value;
    }



    public BitmapDrawable getTextDrawable(String firstText, String secondText) {
        return ((BaseActivity) mContext).getTextDrawable(firstText, secondText);
    }

    public String encodeFileToBase64Binary(File yourFile) {
        return ((BaseActivity) mContext).encodeFileToBase64Binary(yourFile);
    }


}
