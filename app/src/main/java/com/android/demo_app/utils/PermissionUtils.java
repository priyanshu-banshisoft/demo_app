package com.android.demo_app.utils;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;

import com.android.demo_app.R;


public class PermissionUtils {
    public static boolean isGpsDialogShow = false;

    public static boolean isLocationEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            isGpsDialogShow = true;
        }
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public static void showGPSNotEnabledDialog(final Context context) {
        if (!isGpsDialogShow) {

            isGpsDialogShow = true;
        }
    }

}
