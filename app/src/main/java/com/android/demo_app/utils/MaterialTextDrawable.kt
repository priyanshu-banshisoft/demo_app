package com.brring.android.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.drawable.BitmapDrawable
import android.os.Looper
import android.text.TextPaint
import android.widget.ImageView
import java.util.*

class MaterialTextDrawable private constructor(builder: Builder) {

    companion object {
        private const val MaterialDark = 400
        private const val MaterialMedium = 700
        private const val MaterialLight = 900

        fun with(context: Context): Builder = Builder().with(context)
        private fun isOnMainThread(): Boolean = Looper.myLooper() == Looper.getMainLooper()
    }

    enum class MaterialShape {
        CIRCLE,
        RECTANGLE
    }

    enum class MaterialColorMode {
        LIGHT,
        MEDIUM,
        DARK
    }

    private var context: Context
    private var size: Int
    private var colorMode: MaterialColorMode
    private var drawableShape: MaterialShape
    private var firstText: String
    private var secondText: String

    init {
        this.context = builder.context
        this.size = builder.size
        this.colorMode = builder.colorMode
        this.drawableShape = builder.drawableShape
        this.firstText = builder.firstText
        this.secondText = builder.secondText
    }

    class Builder {

        internal lateinit var context: Context
        internal var size = 70
        internal var colorMode: MaterialColorMode = MaterialColorMode.MEDIUM
        internal var drawableShape: MaterialShape = MaterialShape.CIRCLE
        internal var firstText: String = ""
        internal var secondText: String = ""

        constructor()

        fun with(context: Context): Builder {
            this.context = context
            return this
        }

        fun textSize(size: Int): Builder {
            this.size = size
            return this
        }

        fun shape(shape: MaterialShape): Builder {
            this.drawableShape = shape
            return this
        }

        fun colorMode(mode: MaterialColorMode): Builder {
            this.colorMode = mode
            return this
        }

        fun text(firstText: String, secondText: String): Builder {
            this.firstText = firstText
            this.secondText = secondText
            return this
        }

        fun get(): BitmapDrawable {
            if(firstText == ""){
                throw NullPointerException("No text provided, " +
                        "call text(<your_text>) before calling this method")
            }
            return MaterialTextDrawable(this).getTextDrawable()
        }

        fun into(view: ImageView) {
            if(!isOnMainThread()) {
                throw IllegalArgumentException("You must call this method on the main thread")
            }
            // Set text-drawable
            view.setImageDrawable(get())
        }

        fun into(view: ImageView, scale: ImageView.ScaleType) {
            view.scaleType = scale
            into(view)
        }

    }

    private fun getTextDrawable(): BitmapDrawable {
        val initials = if(firstText.length > 1 && secondText.length > 1){
            getFirstChar(firstText, secondText)
        } else if (firstText.length > 1) {
            getFirstChar(firstText, "")
        } else if (secondText.length > 1) {
            getFirstChar(secondText, "")
        } else {
            firstText
        }
        val textPaint = textPainter(calculateTextSize(this.size))
        val painter = Paint()
        painter.isAntiAlias = true

        if(drawableShape == MaterialShape.RECTANGLE) {
//            painter.color = ColorGenerator(getColorMode(colorMode)).getRandomColor()
            painter.color = Color.parseColor("#FFFFFF")
        } else {
            painter.color = Color.TRANSPARENT
        }

        val areaRectangle = Rect(0, 0, 180, size)
        val bitmap = Bitmap.createBitmap(size, size, ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawRect(areaRectangle, painter)

        if(drawableShape == MaterialShape.RECTANGLE) {
            painter.color = Color.TRANSPARENT
        } else {
//            painter.color = ColorGenerator(getColorMode(colorMode)).getRandomColor()
            painter.color = Color.parseColor("#3B3B3B")
        }

        val bounds = RectF(areaRectangle)
        bounds.right = textPaint.measureText(initials, 0, 1)
        bounds.bottom = textPaint.descent() - textPaint.ascent()

        bounds.left += (areaRectangle.width() - bounds.right) / 2.0f
        bounds.top += (areaRectangle.height() - bounds.bottom) / 2.0f

        canvas.drawCircle(size.toFloat() / 2, size.toFloat() / 2, size.toFloat() / 2, painter)
        canvas.drawText(initials, bounds.left, bounds.top - textPaint.ascent(), textPaint)
        return BitmapDrawable(context.resources, bitmap)
    }

    private fun calculateTextSize(size: Int): Float {
        return (size / 4.125).toFloat()
    }

    private fun getFirstChar(firstText: String, secondText: String): String {
        var completeStr = ""
        var firstChar = ""
        var lastChar = ""

        if (!StringHelper.isEmpty(firstText)) {
            firstChar = firstText.first().toString().toUpperCase(Locale.ROOT)
//            if (strSplit.size > 1) {
//                lastChar = strSplit[strSplit.size - 1].first().toString().toUpperCase(Locale.ROOT)
//            }
        }

        if (!StringHelper.isEmpty(secondText)) {
            lastChar = secondText.first().toString().toUpperCase(Locale.ROOT)
//            if (strSplit.size > 1) {
//                lastChar = strSplit[strSplit.size - 1].first().toString().toUpperCase(Locale.ROOT)
//            }
        }

        completeStr = String.format("%s%s", firstChar, lastChar)
        return completeStr
    }

    private fun textPainter(size: Float): TextPaint {
        val textPaint = TextPaint()
        textPaint.isAntiAlias = true
        textPaint.textSize = 100f/* * context.resources.displayMetrics.scaledDensity*/
        textPaint.color = Color.WHITE
        return textPaint
    }

    private fun getBackground (getResources: Resources){
        return when (colorMode){
            else -> {}
        }
    }



    private fun getColorMode(mode: MaterialColorMode): Int {
        return when(mode) {
            MaterialColorMode.LIGHT -> {
                MaterialLight
            }
            MaterialColorMode.MEDIUM -> {
                MaterialMedium
            }
            MaterialColorMode.DARK -> {
                MaterialDark
            }
        }
    }
}