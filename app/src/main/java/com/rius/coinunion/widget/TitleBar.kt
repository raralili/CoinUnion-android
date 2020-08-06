package com.rius.coinunion.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.rius.coinunion.R
import kotlinx.android.synthetic.main.title_bar.view.*

class TitleBar(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    private lateinit var root: View
    lateinit var titleView: TextView
        private set
    lateinit var endActionView: TextView
        private set

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0) {
        root = LayoutInflater.from(context).inflate(R.layout.title_bar, this)
        titleView = root.title
        endActionView = root.end_action

        val ta = context.obtainStyledAttributes(attrs, R.styleable.TitleBar)
        val titleStr = ta.getString(R.styleable.TitleBar_title)
        setTitle(titleStr)

        val endActionText = ta.getString(R.styleable.TitleBar_endActionText)
        val booleanEndActon = ta.getBoolean(R.styleable.TitleBar_enableEndAction, true)
        setEndActionText(endActionText)
        enableEndAction(booleanEndActon)
        ta.recycle()
    }

    fun setTitle(title: String?) {
        titleView.text = title
    }

    fun setTitleColor(color: Int) {
        titleView.setTextColor(color)
    }

    fun setEndActionText(str: String?) {
        endActionView.text = str
    }

    fun enableEndAction(enable: Boolean) {
        endActionView.visibility = if (enable) View.VISIBLE else View.GONE
    }

    fun setEndAction(action: (v: View) -> Unit) {
        endActionView.setOnClickListener { action(it) }
    }
}