package com.android.demo_app.helper;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class MarshMallowPermission {
    public static int checkPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission);
    }

    public static boolean checkPermission(Context context, String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }

        return true;
    }

    public static boolean checkMashMallowPermissions(AppCompatActivity appCompatActivity, String[] permissionsToGrant, int PERMISSION_REQUEST_CODE) {
        ArrayList<String> permissions = new ArrayList<>();
        for (String permission : permissionsToGrant) {
            if (checkPermission(appCompatActivity, permission) != 0) {
                permissions.add(permission);
            }
        }
        if (permissions.size() == 0) {
            return true;
        }
        ActivityCompat.requestPermissions(appCompatActivity,  permissions.toArray(new String[permissions.size()]), PERMISSION_REQUEST_CODE);
        return false;
    }
}
