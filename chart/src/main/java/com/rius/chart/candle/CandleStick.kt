package com.rius.chart.candle

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class CandleStick(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var candleStickInfo: CandleStickInfo? = null
        set(value) {
            field = value
            invalidate()
        }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (candleStickInfo == null) return
        val info = candleStickInfo!!
        //画柱子
        val rectF: RectF

        if (info.open >= info.close) { //开大于收
            paint.color = Color.RED
            rectF = RectF(0f, info.open, 10f, info.close)
        } else {
            paint.color = Color.BLUE
            rectF = RectF(0f, info.close, 10f, info.open)
        }
        canvas.drawRect(rectF, paint)

        //画线
        canvas.drawLine(4f, 0f, 6f, info.high, paint)
        canvas.drawLine(4f, 0f, 6f, info.low, paint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        //wrap_content
        val width = 10
        val height = 50
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(width, height)
        }
    }
}