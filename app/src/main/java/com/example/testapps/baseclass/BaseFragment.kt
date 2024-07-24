package com.example.testapps.baseclass

import android.app.Activity
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.testapps.utils.CustomProgressbar
import com.vital.radicube.utils.showSuccessToast
import com.google.android.material.snackbar.Snackbar


abstract class BaseFragment: Fragment() {
    abstract fun initViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }


    fun setViewModel(viewModel: BaseViewModel) {

        viewModel.mIsLoading
            .observe(this, Observer { aBoolean: Boolean ->
                if (aBoolean) {
                    showProgressbar()
                } else {
                    dismissProgressbar()
                }
            })

        viewModel.mFailureMessage.observe(this, Observer {
            // this is the place where we are getting all error toast message
            // we manually changed api error response to meaning full message as "Check Your InterNet Connection"
            if (isOnline()){
               // showSuccessToast(it)
            }else{
                showSuccessToast("Check Your InterNet Connection")
            }

            println("BaseFragment mFailureMessage "+it)
           // showSuccessToast("Check Your InterNet Connection")
        })
    }

    private fun showProgressbar() {
        CustomProgressbar.getProgressbar(context as Context, false).show()
    }

    private fun dismissProgressbar() {
        CustomProgressbar.getProgressbar(context as Context, false).dismiss()

    }

    fun getStringFromId(id: Int): String {
        return (activity as Activity).resources.getString(id)
    }

    fun getIntegerFromId(id: Int): Int {
        return (activity as Activity).resources.getInteger(id)
    }


    fun showToast(msg: String?) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }
    open fun showSnackBar(activity: Activity, message: String?) {
        val rootView: View = activity.window.decorView.findViewById(android.R.id.content)
       // Snackbar.make(rootView, message!!, Snackbar.LENGTH_SHORT).show()
        Snackbar.make(rootView, message!!, Snackbar.LENGTH_SHORT).show()
        val snack: Snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_LONG)
        val view = snack.view
        val tv = view.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
        tv.setTextColor(Color.WHITE)
        snack.show()
    }

    fun isOnline(): Boolean {
        val cm = context?.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val n = cm.activeNetwork
            if (n != null) {
                val nc = cm.getNetworkCapabilities(n)
                //It will check for both wifi and cellular network
                return nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(
                    NetworkCapabilities.TRANSPORT_WIFI)
            }
            return false
        } else {
            val netInfo = cm.activeNetworkInfo
            return netInfo != null && netInfo.isConnectedOrConnecting
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




}