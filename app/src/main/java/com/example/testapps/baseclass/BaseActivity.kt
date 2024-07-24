package com.example.testapps.baseclass

import android.app.Activity
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.testapps.utils.CustomProgressbar
import com.google.android.material.snackbar.Snackbar


abstract class BaseActivity : AppCompatActivity() {

    abstract fun initViewModel()
    abstract fun getLayoutResourceId(): Int
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResourceId())
        initViewModel()
        setAppFontScale()
    }

    fun setViewModel(viewModel: BaseViewModel) {
        viewModel.mIsLoading.observe(this, Observer { aBoolean: Boolean ->
            if (aBoolean) {
                showProgressbar()
            } else {
                dismissProgressbar()
            }
        })
        viewModel.mFailureMessage.observe(
            this,
            Observer { msg: String ->
                dismissProgressbar()
                // this is the place where we are getting all error toast message
                // we manually changed api error response to meaning full message as "Check Your InterNet Connection"
                if (isOnline()){
                     showMessage(msg)
                }else{
                    showMessage("Check your Internet Connection")
                }

                println("BaseActivity mFailureMessage "+msg)
                // showMessage("Check Your InterNet Connection")
            }
        )
        viewModel.mSuccessMessage.observe(
            this,
            Observer { msg: String ->
                dismissProgressbar()
                showMessage(msg)
            }
        )
//        viewModel.mAlertMessages.observe(
//            this,
//            Observer { msg: String ->
//                dismissProgressbar()
//                Log.e("BaseActivity ","mAlertMessages "+msg)
//                //showAlertDialog(msg)
//            }
//        )
    }

    override fun onResume() {
        super.onResume()
        if (sAppMinimized) {
            sAppMinimized = false
        }
        sAppAlive = true
    }

    override fun onPause() {
        super.onPause()
        sAppAlive = false
    }

    override fun onStop() {
        super.onStop()
        if (!sAppAlive) {
            sAppMinimized = true
        }
    }

    fun showProgressbar() {
        if (!isFinishing) {
            CustomProgressbar.getProgressbar(this, false).show()

        }
    }


    fun dismissProgressbar() {
        if (!isFinishing) {
            CustomProgressbar.getProgressbar(this, false).dismiss()
            // hud?.dismiss()
        }
    }

    fun showMessage(message: String) {
        Toast.makeText(this@BaseActivity,message, Toast.LENGTH_SHORT).show()
    }

    open fun showSnackBar(activity: Activity, message: String?) {
      /*  val rootView: View = activity.window.decorView.findViewById(android.R.id.content)
        Snackbar.make(rootView, message!!, Snackbar.LENGTH_SHORT).show()*/
        val rootView: View = activity.window.decorView.findViewById(android.R.id.content)
        Snackbar.make(rootView, message!!, Snackbar.LENGTH_SHORT).show()
        val snack: Snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_LONG)
        val view = snack.view
        val tv = view.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
        tv.setTextColor(Color.WHITE)
        snack.show()
    }

    fun isOnline(): Boolean {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val n = cm.activeNetwork
            if (n != null) {
                val nc = cm.getNetworkCapabilities(n)
                //It will check for both wifi and cellular network
                nc?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true || nc?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true
            } else {
                false
            }
        } else {
            val netInfo = cm.activeNetworkInfo
            netInfo != null && netInfo.isConnectedOrConnecting
        }
    }
    //Note: To avoid double click
    private var lastClickTime: Long = 0
    private val clickThreshold = 1000
    protected fun setSafeOnClickListener(view: View, onClickListener: (View) -> Unit) {
        view.setOnClickListener { v ->
            if (SystemClock.elapsedRealtime() - lastClickTime < clickThreshold) {
                return@setOnClickListener
            }
            lastClickTime = SystemClock.elapsedRealtime()
            onClickListener(v)
        }
    }


    private fun setAppFontScale() {
        // Set your desired font scale here (1.0 is the default scale)
        val fontScale = 1.0f

        // Update the configuration with the desired font scale
        resources.configuration.fontScale = fontScale

        // Apply the configuration change
        val metrics = resources.displayMetrics
        baseContext.resources.updateConfiguration(resources.configuration, metrics)
    }

    companion object {

        var sAppAlive: Boolean = false
        var sAppMinimized = true


    }
}