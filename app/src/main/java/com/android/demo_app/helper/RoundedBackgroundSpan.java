package com.android.demo_app.helper;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;

import com.android.demo_app.utils.ViewUtil;


public class RoundedBackgroundSpan extends ReplacementSpan {

    private static final int CORNER_RADIUS = ViewUtil.dpToPx(8);

    private static final float PADDING_X = ViewUtil.dpToPx(12);
    private static final float PADDING_Y = ViewUtil.dpToPx(5);

    private static final float MAGIC_NUMBER = ViewUtil.dpToPx(2);

    private int mBackgroundColor;
    private int mTextColor;
    private float mTextSize;

    /**
     * @param backgroundColor color value, not res id
     * @param textSize        in pixels
     */
    public RoundedBackgroundSpan(int backgroundColor, int textColor, float textSize) {
        mBackgroundColor = backgroundColor;
        mTextColor = textColor;
        mTextSize = textSize;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        float width = paint.measureText(text.subSequence(start, end).toString());
        RectF rect = new RectF(x, top-15, x + width + 2 * PADDING_X, bottom+15);
        paint.setColor(mBackgroundColor);
        canvas.drawRoundRect(rect, CORNER_RADIUS, CORNER_RADIUS, paint);
        paint.setColor(mTextColor);
        canvas.drawText(text, start, end, x + PADDING_X, y, paint);
    }

    private int getTagWidth(CharSequence text, int start, int end, Paint paint) {
        return Math.round(PADDING_X + paint.measureText(text.subSequence(start, end).toString()) + PADDING_X);
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        paint = new Paint(paint); // make a copy for not editing the referenced paint
        paint.setTextSize(mTextSize);
        return getTagWidth(text, start, end, paint);
    }
}