package com.android.demo_app.utils;

import android.util.Log;

import java.io.File;

public class FilesUtill {
    public static final long ONE_KB = 1024;
    public static final long ONE_MB = ONE_KB * ONE_KB;
    public static final long ONE_GB = ONE_KB * ONE_MB;

    public  static boolean checkMaxFileSize(File file) {
        double maxFileSize = 3 * 1024 * 1024;
        Long l = file.length();
        String sizeNew = "";
        double size = (double) l / 1000.0;
        if (size >= 1024) {
            sizeNew = (size / 1024) + " MB";
        } else {
            sizeNew = size + " KB";
        }
        String fileSize = l.toString();
        Log.e("fileSize", sizeNew);
        int finalFileSize = Integer.parseInt(fileSize);
        return finalFileSize >= maxFileSize;
    }

    public static String getFileSize(File file){
        Long l = file.length();
        String sizeNew = "";
        double size = (double) l / 1000.0;
        if (size >= 1024) {
            sizeNew = (size / 1024) + " mb";
        } else {
            sizeNew = size + " kb";
        }
        String[] sizeVal = sizeNew.split(" ");
        String fileSize = l.toString();
        if (sizeVal.length > 0) {
            fileSize = StringUtill.formatDouble(Double.parseDouble(sizeVal[0])) + sizeVal[1];
        }
        return fileSize;
    }
}
