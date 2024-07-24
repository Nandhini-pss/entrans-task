package com.example.testapps.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.RelativeLayout.LayoutParams
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.testapps.R


class CustomProgressbar : Dialog {

    private var mMessage: String? = null

    private var mCancellable: Boolean = false

    constructor(context: Context) : super(context) {
        mMessage = null
        createProgressBar(context)
    }

    constructor(context: Context, message: String) : super(context) {
        mMessage = message
        createProgressBar(context)
    }

    fun getProgressbarInView(context: Context): View {
        val progressBar = ProgressBar(context, null,
                android.R.attr.progressBarStyleLarge)
        val resources = context.resources
        val dimension = resources.getDimensionPixelSize(R.dimen.dp_50)
        val transparentColor = resources.getColor(android.R.color.transparent)
        progressBar.setBackgroundColor(transparentColor)
        progressBar.visibility = View.VISIBLE
        progressBar.id = R.id.progress_circular
        val progressBarLayoutParams = LayoutParams(dimension,
                dimension)
        progressBarLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT)
        progressBar.layoutParams = progressBarLayoutParams
        val layout = RelativeLayout(context)
        var params = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        layout.layoutParams = params
        layout.setBackgroundColor(transparentColor)
        layout.gravity = Gravity.CENTER
        layout.addView(progressBar)
        layout.bringToFront()
        if (null != mMessage) {
            params = LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT)
            val messageTextView = TextView(context)
            messageTextView.text = mMessage
            messageTextView.typeface = Typeface.DEFAULT_BOLD
            messageTextView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_blue_dark))
            params.addRule(RelativeLayout.BELOW, progressBar.id)
            params.setMargins(0, resources.getDimensionPixelSize(R.dimen.dp_20), 0, 0)
            messageTextView.layoutParams = params
            layout.addView(messageTextView)
        }
        return layout
    }

    private fun createProgressBar(context: Context) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(getProgressbarInView(context))
    ///    setContentView(R.layout.custom_progressbar)
        val window = window
        window!!.setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        window.setBackgroundDrawableResource(android.R.color.transparent)
        setCanceledOnTouchOutside(false)
    }

    private fun getAppProgressbar(context: Context): View {
        val progressBar = ProgressBar(context, null,
                android.R.attr.progressBarStyleLarge)
        progressBar.id = R.id.progress_circular
        progressBar.isIndeterminate = true
        if (null != mMessage) {
            val relativeLayout = RelativeLayout(context)
            relativeLayout.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT)
            var layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT)
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT)
            progressBar.layoutParams = layoutParams
            relativeLayout.addView(progressBar)
            val messageTextView = TextView(context)
            messageTextView.text = mMessage
            messageTextView.typeface = Typeface.DEFAULT_BOLD
            messageTextView.setTextColor(ContextCompat.getColor(context, R.color.black))
            layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            layoutParams.addRule(RelativeLayout.BELOW, progressBar.id)
            layoutParams.setMargins(0, 20, 0, 0)
            messageTextView.setPadding(0, 20, 0, 0)
            messageTextView.layoutParams = layoutParams
            relativeLayout.addView(messageTextView)
            return relativeLayout
        }

        return progressBar
    }


    override fun show() {
        setCancelable(mCancellable)
        if (isShowing) {
            try {
                super.dismiss()
            }catch (i : IllegalArgumentException){

            }

        }
        super.show()
    }

    override fun dismiss() {
        super.dismiss()
        progressbar = null
    }

    companion object {

        var progressbar: CustomProgressbar? = null
            private set

        private val flags = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        fun getProgressbar(context: Context, cancellable: Boolean): CustomProgressbar {
            if (null == progressbar) {
                progressbar = CustomProgressbar(context)
            }

            //progressbar!!.window!!.decorView.systemUiVisibility = flags
            progressbar!!.mCancellable = cancellable
            return progressbar!!
        }

        fun getProgressbar(context: Context, cancellable: Boolean, message: String): CustomProgressbar {
            if (null == progressbar) {
                progressbar = CustomProgressbar(context, message)
            }
            //progressbar!!.window!!.decorView.systemUiVisibility = flags
            progressbar!!.mCancellable = cancellable
            return progressbar!!
        }
    }
}