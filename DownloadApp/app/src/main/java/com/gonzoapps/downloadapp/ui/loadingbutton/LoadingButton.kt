package com.gonzoapps.downloadapp.ui.loadingbutton

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.gonzoapps.downloadapp.R
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0
    private val cornerRadius = 0.0f
    private var textRect = Rect()
    private var progress: Float = 0f


    private var buttonState: ButtonState by Delegates.observable(ButtonState.Completed) { property, old, new ->
        when(new) {
            ButtonState.Loading -> {
                setText("Downloading")
            }

            ButtonState.Completed -> {
                setText("Download")
//                valueAnimator.cancel()
            }

            ButtonState.Clicked -> {
//                valueAnimator.cancel()
            }
        }

    }

    private var buttonText = String()

    private val buttonBackgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color =  ContextCompat.getColor(context, R.color.colorPrimaryDark)
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        isAntiAlias = true
        color = Color.WHITE
        style = Paint.Style.FILL
        textSize = 40.0f
    }


    init {
        isClickable = true
        setState(ButtonState.Completed)
    }

    fun setState(state: ButtonState) {
        buttonState = state
    }

    private fun setText(text: String) {
        buttonText = text
        invalidate()
        requestLayout()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val buttonWidth = width.toFloat()
        val buttonHeight = height.toFloat()

        canvas?.drawRoundRect(0f, 0f, buttonWidth, buttonHeight, cornerRadius, cornerRadius, buttonBackgroundPaint)
        textPaint.getTextBounds(buttonText, 0, buttonText.length, textRect)
        val centerX = measuredWidth.toFloat() / 2 - textRect.centerX()
        val centerY = measuredHeight.toFloat() / 2 + 12

        canvas?.drawText(buttonText,centerX, centerY, textPaint)

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
                MeasureSpec.getSize(w),
                heightMeasureSpec,
                0
        )
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)
    }

}