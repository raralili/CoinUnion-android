package com.rius.coinunion.widget

import android.content.Context
import android.util.AttributeSet
import com.rius.coinunion.R
import java.text.NumberFormat

/**
 *
 */
class FluctuationTextView : RoundCornerTextView {

    constructor(context: Context?) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setTextColor(resources.getColor(R.color.colorWhite, context.theme))
        val ta = context.obtainStyledAttributes(attrs, R.styleable.FluctuationTextView)
        val fluctuation = ta.getFloat(R.styleable.FluctuationTextView_fluctuation, 0f)
        setFluctuation(fluctuation)
        ta.recycle()
    }

    fun setFluctuation(fluctuation: Float) {
        setBackgroundByFluctuation(fluctuation)
        setTextByFluctuation(fluctuation)
    }

    private fun setTextByFluctuation(fluctuation: Float) {
        val percentFormat = NumberFormat.getPercentInstance()
        percentFormat.maximumFractionDigits = 2
        percentFormat.minimumFractionDigits = 2
        val result = percentFormat.format(fluctuation)
        this.text = if (fluctuation > 0f) "+${result}" else result
    }

    private fun setBackgroundByFluctuation(fluctuation: Float) {
        if (fluctuation > 0f) {
            setBackgroundColor(resources.getColor(R.color.colorIncrease, context.theme))
        } else if (fluctuation < 0f) {
            setBackgroundColor(resources.getColor(R.color.colorDecrease, context.theme))
        } else if (fluctuation == 0f) {
            setBackgroundColor(resources.getColor(R.color.colorNoChange, context.theme))
        }
    }
}