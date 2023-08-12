package com.android.demo_app.app;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.android.demo_app.R;
import com.android.demo_app.helper.PreferenceManger;
import com.android.demo_app.utils.StatusBarUtils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class BaseBottomSheetDialogFragment extends BottomSheetDialogFragment {

    public Context mContext;
    public PreferenceManger preferenceManger;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public void setupFullHeight(BottomSheetDialog bottomSheetDialog) {
        FrameLayout bottomSheet = (FrameLayout) bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();

        int windowHeight = getWindowHeight();
        if (layoutParams != null) {
            layoutParams.height = windowHeight - 300;
        }
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    public int getWindowHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setLightStatusBar((Activity) mContext, R.color.white);

        preferenceManger = ((BaseActivity) getActivity()).preferenceManger;
    }

    public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public void showSnackBar(View view, String msg) {
        Snackbar.make(view,msg, Snackbar.LENGTH_LONG).show();
    }

    public RequestBody toRequestBody (String value) {
        return !TextUtils.isEmpty(value) ? RequestBody.create(MediaType.parse("text/plain"), value) : null;
    }

    public String capitalizeFirstLetter(String str) {
        if(str == null) return "";
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

}