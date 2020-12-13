package com.gonzoapps.downloadapp.ui.loadingbutton

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import com.gonzoapps.downloadapp.R
import kotlin.properties.Delegates


class LoadingButton @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0
    private var textRect = Rect()
    private var circle = RectF()
    private var progress: Float = 0f
    private var animator = ValueAnimator()
    private var circleStartAngle = 0f


    private var buttonState: ButtonState by Delegates.observable(ButtonState.Completed) { property, old, new ->
        when(new) {
            ButtonState.Loading -> {
                setText("Downloading")
                invalidate()
                startAnimation()
            }

            ButtonState.Completed -> {
                setText("Download")
                invalidate()
                progress = 0.0f
                animator.cancel()
            }

            ButtonState.Clicked -> {
                progress = 0.0f
                invalidate()
                animator.cancel()
            }
        }

    }

    private fun startAnimation() {
        animator = ValueAnimator.ofFloat(0f, 1f).apply {
            addUpdateListener {
                progress = animatedValue as Float
                invalidate()
            }
            interpolator = LinearInterpolator()
            repeatMode = ValueAnimator.REVERSE
            repeatCount = ValueAnimator.INFINITE
            duration = 2000
            invalidate()
        }
        animator.start()
    }

    private var buttonText = String()

    private var buttonForegroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private var buttonBackgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private var circleForegroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color =  ContextCompat.getColor(context, R.color.colorAccent)
    }

    private var textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        textSize = 40.0f
    }

    init {
        setState(ButtonState.Completed)
        context.theme.obtainStyledAttributes(attrs, R.styleable.LoadingButton, 0,0).apply {
            buttonBackgroundPaint.color = getColor(R.styleable.LoadingButton_buttonBackgroundColor, ContextCompat.getColor(context, R.color.colorPrimaryDark))
            buttonForegroundPaint.color = getColor(R.styleable.LoadingButton_buttonForegroundColor, ContextCompat.getColor(context,R.color.colorPrimary))
            textPaint.color = getColor(R.styleable.LoadingButton_buttonTextColor, ContextCompat.getColor(context,R.color.white))
            recycle()
        }
    }

    fun setState(state: ButtonState) {
        buttonState = state
    }

    private fun setText(text: String) {
        buttonText = text
        invalidate()
        requestLayout()
    }

    private fun getCircleAngle() : Float {
        return progress * 360f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.apply {
            save()

            // Draw Button Background Color
            drawPaint(buttonBackgroundPaint)

            // Draw Progress Button
            val progressWidth = progress * widthSize
            drawRect(0f, 0f, progressWidth, heightSize.toFloat(), buttonForegroundPaint)

            // Draw Progress Circle
            val circleSize = heightSize / 2.0f
            val circleCenterHorizontalOffset = widthSize/2f + textRect.centerX() + 20.0f;
            val circleCenterVerticalOffset = heightSize/2f - circleSize/2;
            circle.set(
                    circleCenterHorizontalOffset,
                    circleCenterVerticalOffset,
                    circleCenterHorizontalOffset + circleSize,
                    circleCenterVerticalOffset + circleSize)
            drawArc(circle, circleStartAngle, getCircleAngle(), true, circleForegroundPaint)

            // Draw & Position Text
            textPaint.getTextBounds(buttonText, 0, buttonText.length, textRect)
            val centerX = widthSize.toFloat() / 2 - textRect.centerX()
            val centerY = heightSize.toFloat() / 2 + 12
            drawText(buttonText, centerX, centerY, textPaint)

            restore()
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(MeasureSpec.getSize(w), heightMeasureSpec, 0)
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)
    }

}