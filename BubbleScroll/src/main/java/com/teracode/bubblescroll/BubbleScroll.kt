package com.teracode.bubblescroll

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "BubbleScroll"

class BubbleScroll : LinearLayout {
    private var btnBackground: Int = 0
    private var btnText: String? = null

    var text: String?
        get() :String? {
            return btnText
        }
        set(value) {
            btnText = value
            onBtnTextChanged(btnText)
        }

    private lateinit var button: Button
    private lateinit var recyclerView: RecyclerView

    private val onScrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            onBtnTextChanged("$dx  $dy")
//            recyclerView.get
        }
    }


    fun attachToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
        setupCallbacks()
    }


    private fun setupCallbacks() = recyclerView?.addOnScrollListener(onScrollListener)

    constructor(context: Context) : super(context) {
        val root = LayoutInflater.from(context).inflate(R.layout.bubble_scroll, this, true)
        button = root.findViewById<Button>(R.id.bubble_btn)
        setAttributes()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val root = LayoutInflater.from(context).inflate(R.layout.bubble_scroll, this, true)
        button = root.findViewById(R.id.bubble_btn)

        setAttributes(attrs)
    }

    private fun onBtnTextChanged(value: String?) {
        with(button) {
            text = value
        }
    }


    private fun setAttributes(
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
    ) {
        with(
            context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.BubbleScroll,
                defStyleAttr,
                defStyleRes
            )
        ) {

            btnBackground = getColor(
                R.styleable.BubbleScroll_scrollBackground,
                ContextCompat.getColor(context, R.color.default_background_color)
            )

            btnText = getString(R.styleable.BubbleScroll_btnText)

            return@with
        }
        with(button) {
            this.text = btnText
            this.setBackgroundColor(btnBackground)
        }


    }
}