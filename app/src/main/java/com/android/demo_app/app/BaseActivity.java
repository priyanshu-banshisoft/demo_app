package com.android.demo_app.app;




import android.Manifest;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.android.demo_app.R;
import com.android.demo_app.helper.AppProgressBar;
import com.android.demo_app.helper.PreferenceManger;
import com.android.demo_app.utils.KeyboardUtils;
import com.android.demo_app.utils.StatusBarUtils;
import com.android.demo_app.utils.ViewUtil;
import com.androidadvance.topsnackbar.TSnackbar;

import com.brring.android.utils.MaterialTextDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.RequestBody;


public class BaseActivity extends AppCompatActivity {

    public final int REQ_CODE_CAMERA = 1;
    public final int PIC_CROP = 200;

    public String NO_DATA_FOUND = "No Record Found!";
    public String SOMETHING_WENT_WRONG = "Something went wrong!";
    public String FAILED_TEXT = "Something went wrong!";
    public String SESSION_EXPIRED_TEXT = "Session expired,Please Login Again.";

    public PreferenceManger preferenceManger;

    Bundle bndlAnimation;

    public String[] locationPermission = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    public String[] mediaPermissions = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public String[] docPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public String[] cameraPermissions = {Manifest.permission.CAMERA};

    public boolean isLocationPermissionEnable, isCameraPermissionEnable, isStoragePermissionEnable;

    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

    public RequestBody toRequestBody(String value) {
        return !TextUtils.isEmpty(value) ? RequestBody.create(MediaType.parse("text/plain"), value) : null;
    }

    public void checkPermissionAuthorise() {
        isLocationPermissionEnable = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        isCameraPermissionEnable = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;

        isStoragePermissionEnable = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setLightStatusBar(this, R.color.white);
        //preferenceManger = BaseApplication.getPreferenceManger();

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    public String getMimeType(Context context, Uri uri) {
        String mimeType = null;
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme())) {
            ContentResolver cr = context.getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType;
    }

    public void getFileSize(File file) {
        int maxFileSize = 2 * 1024 * 1024;
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
    }

    public boolean isFileMoreThan2MB(File file) {
        int maxFileSize = 2 * 1024 * 1024;
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

    public boolean isFileMoreThan25MB(File file) {
        int maxFileSize = 25 * 1024 * 1024;
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



    public void showAlertDialog(String title, String msg, String positiveBtn, String negativeBtn) {
        showAlertDialog(title, msg, positiveBtn, negativeBtn, null, null);
    }

    public void showAlertDialog(String title, String msg, String positiveBtn, String negativeBtn, DialogInterface.OnClickListener positiveCallBtn) {
        showAlertDialog(title, msg, positiveBtn, negativeBtn, positiveCallBtn, null);
    }

    public void showAlertDialog(String title, String msg, String positiveBtn, String negativeBtn, DialogInterface.OnClickListener positiveCallBtn, DialogInterface.OnClickListener negativeCallBtn) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(positiveBtn, positiveCallBtn);
        builder.setNegativeButton(negativeBtn, negativeCallBtn);
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void showSnackBar(View view, String msg) {
        TSnackbar snackbar = TSnackbar.make(view,msg, TSnackbar.LENGTH_LONG);
        TextView textView = snackbar.getView().findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        // snackbar.setActionTextColor(Color.WHITE);
        snackbar.show();
    }


    public void showLoader() {
        AppProgressBar.showLoader(this);
    }

    public void hideLoader() {
        AppProgressBar.hideLoaderDialog();
    }

    public void clearAllNotification() {
        try {
            NotificationManagerCompat.from(this).cancelAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCountryCode(String countryName) {
        String[] isoCountryCodes = Locale.getISOCountries();
        for (String code : isoCountryCodes) {
            Locale locale = new Locale("", code);
            if (countryName.equalsIgnoreCase(locale.getDisplayCountry())) {
                return code;
            }
        }
        return "";
    }

    public boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public File createImageFile() throws IOException {
        String imageFileName = "BRRING_Image-" + System.currentTimeMillis() + "_";
        File storageDir = getFilesDir();
        File image = File.createTempFile(imageFileName, ".png", storageDir);
        return image;
    }

    public File createVideoFile() throws IOException {
        String videoFileName = "FRF Video-" + System.currentTimeMillis() + "_";
        File storageDir = getFilesDir();
        File video = File.createTempFile(videoFileName, ".mp4", storageDir);
        return video;
    }

    public String encodeFileToBase64Binary(File yourFile) {
        int size = (int) yourFile.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(yourFile));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }

    public int getIntFromString(String value) {
        if (TextUtils.isEmpty(value))
            return 0;
        else return Integer.parseInt(value);
    }

    public void shareData(String filename, String content) {
        Uri imageUri = Uri.fromFile(new File("//android_asset/" + filename));
        Glide.with(this)
                .asBitmap()
                .load(imageUri)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        try {
                            //  hideLoader();
                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.putExtra(Intent.EXTRA_TEXT, content + "\n" + "https://www.Google.com");
                            String path = MediaStore.Images.Media.insertImage(getContentResolver(), resource, "", null);
                            Uri screenshotUri = Uri.parse(path);
                            intent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                            intent.setType("image/*");
                            startActivity(Intent.createChooser(intent, "Share image via..."));
                        } catch (Exception exception) {
                            //  hideLoader();
                            Log.e("error", Objects.requireNonNull(exception.getMessage()));
                        }
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        //  hideLoader();
                    }
                });
    }

    public BitmapDrawable getTextDrawable(String firstText, String secondText) {
        return MaterialTextDrawable.Companion.with(this)
                .text(firstText, secondText)
                .colorMode(MaterialTextDrawable.MaterialColorMode.LIGHT)
                .textSize(250)
                .shape(MaterialTextDrawable.MaterialShape.CIRCLE)
                .get();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (this.getCurrentFocus() != null) {
            KeyboardUtils.hideSoftKeyboard(this);
        }
        return super.dispatchTouchEvent(ev);
    }




    public boolean isIntentArgumentEmpty() {
        if (getIntent() != null) {
            return getIntent().getExtras() == null;
        }
        return true;
    }

    public void setLocaleLanguage(String lang) { //call this in onCreate()
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
    }



}




