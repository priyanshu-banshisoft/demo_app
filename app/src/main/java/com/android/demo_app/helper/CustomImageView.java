package com.android.demo_app.helper;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomImageView extends androidx.appcompat.widget.AppCompatImageView {
    int cachedWidth = 0;
    int cachedHeight = 0;

    public CustomImageView(@NonNull Context context) {
        super(context);
    }

    public CustomImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        try {
//            Drawable drawable = getDrawable();
//
//            if (drawable == null) {
//                setMeasuredDimension(0, 0);
//            } else {
//                float imageSideRatio = (float) drawable.getIntrinsicWidth() / (float) drawable.getIntrinsicHeight();
//                float viewSideRatio = (float) MeasureSpec.getSize(widthMeasureSpec) / (float) MeasureSpec.getSize(heightMeasureSpec);
//                if (imageSideRatio >= viewSideRatio) {
//                    // Image is wider than the display (ratio)
//                    int width = MeasureSpec.getSize(widthMeasureSpec);
//                    int height = (int) (width / imageSideRatio);
//                    setMeasuredDimension(width, height);
//                } else {
//                    // Image is taller than the display (ratio)
//                    int height = MeasureSpec.getSize(heightMeasureSpec);
//                    int width = (int) (height * imageSideRatio);
//                    setMeasuredDimension(width, height);
//                }
//            }
//        } catch (Exception e) {
//            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        }
//    }
//private void addToCache() {
//    mDimenCache.put(groupId, new DimenPair(cachedWidth, cachedHeight));
//}
//    private class DimenPair {
//        public DimenPair(int w, int h) {
//            this.width = w;
//            this.height = h;
//        }
//        int width;
//        int height;
//    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);




        Drawable d = getDrawable();
        if (d != null) {
            float ratio = (float) getMeasuredWidth() / (float) d.getIntrinsicWidth();
            int imgHeight = (int) (d.getIntrinsicHeight() * ratio);
            int imgWidth = (int) (d.getIntrinsicWidth() * ratio);

                cachedWidth = imgWidth;
                cachedHeight = imgHeight;

            setMeasuredDimension(cachedWidth, cachedHeight);

        } else {
            setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
        }
    }
}
