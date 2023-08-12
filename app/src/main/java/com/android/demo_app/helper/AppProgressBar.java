package com.android.demo_app.helper;

import android.content.Context;
import android.graphics.Color;

import com.android.demo_app.R;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class AppProgressBar {
    public static ACProgressFlower dialog;
    public static void showLoader(Context context) {

        try {
            if (dialog != null)
                if (dialog.isShowing())
                    dialog.dismiss();
            dialog = new ACProgressFlower.Builder(context)
                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                    .themeColor(context.getColor(R.color._42B4E5))
                    .bgColor(Color.WHITE)
                    .bgAlpha((float) 1.0)
                    .fadeColor(Color.BLACK).build();
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void hideLoaderDialog() {
        try {
            if (dialog != null && dialog.isShowing())
                dialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

