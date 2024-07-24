package com.vital.radicube.utils

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.webkit.URLUtil
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.regex.Pattern


/*
* Handle all validations here
* Created by Velmurugan 29-05-2020
* */
object Validation {

    fun isValidMailId(textToCheck: String?): Boolean {
        val EMAIL_PATTERN =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]" + "+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        val pattern = Pattern.compile(EMAIL_PATTERN)
        val matcher = pattern.matcher(textToCheck)
        return !(textToCheck?.length == 0 || !matcher.matches())
    }

    fun fullname_multiplespaceValidation(n:String):Boolean{
        return n.matches(".*\\s{2}.*".toRegex())
    }
    fun fullname_spaceValidation_atend(n:String):Boolean{
        return n.matches(".*\\s".toRegex())
    }
    fun fullname_beging_space_validation(n:String):Boolean{
        return n.matches("\\s.*".toRegex())
    }

    fun isValidUrl(url: String): Boolean {
        if (isEmpty(url)) return false
        val p = Patterns.WEB_URL
        val m = p.matcher(url.toLowerCase())
        return m.matches()
    }

    fun  isHttpUrl(url: String):Boolean{
        if(URLUtil.isHttpUrl(url) || URLUtil.isHttpsUrl(url)){
            return true
        }
        else
            return false
    }

    fun isValidPwd(txt: String): Boolean {
        return txt.length >= 6
    }

    fun isValidPostCode(txt: String): Boolean {
        return txt.length >= 3
    }

    fun isValidMobile(numberToCheck: String?): Boolean {
        val MOBILE_PATTERN =
            "^\\({0,1}((0|\\+61)(2|4|3|7|8)){0,1}\\){0,1}(\\ |-){0,1}[0-9]{2}(\\ |-){0,1}[0-9]{2}(\\ |-){0,1}[0-9]{1}(\\ |-){0,1}[0-9]{3}$"
        val pattern = Pattern.compile(MOBILE_PATTERN)
        val matcher = pattern.matcher(numberToCheck)
        return !(numberToCheck?.length == 0 || !matcher.matches())
    }

    fun isValidPhone(phone: String?): Boolean {
        return if (TextUtils.isEmpty(phone)) {
            false
        } else {
            Patterns.PHONE.matcher(phone).matches()
        }
    }

    fun isValidMobileNo(mobileNumber: String): Boolean {
        val regExp = Regex("^[6-9]\\d{9}$")
        var isvalid = false
        if (mobileNumber.length > 0) {
            isvalid = mobileNumber.matches(regExp)
        }
        return isvalid
    }

    fun isValidMobileNozero(mobileNumber: String): Boolean {
        val regExp = Regex("^[6-9]\\d{9}$")
        var isvalid = false
        if (mobileNumber.length > 0) {
            isvalid = mobileNumber.matches(regExp)
        }
        return isvalid
    }

    fun isValidName(txt: String): Boolean {
        return txt.length >= 3
    }

    fun isEmpty(txt: String?): Boolean {
        return txt == null || txt.trim { it <= ' ' } == "" || txt.isEmpty()
    }

    fun isEmpty(txt: Int?): Boolean {
        return txt == null
    }

    fun isGreaterthanZero(txt: Int?): Boolean {
        return  txt!! <= 0
    }

    fun isGreaterthanValue(val1: Int?, val2: Int?): Boolean {
        return val1!! >= val2!!
    }
/*
    fun isVaildPin(pin: String): Boolean {
        val regExp = Regex("^[1-9]{4}$")
        var isvalid = false
        if (pin.length > 0) {
            isvalid = pin?.matches(regExp)
        }
        return isvalid
    }*/

    fun isMICRNumber(micrNumber: String) : Boolean {
        val regExp = Regex("^[0-9]{9}$")
        var isvalid = false
        if (micrNumber.length > 0) {
            isvalid = micrNumber.matches(regExp)
        }
        return isvalid
    }
    fun isAccountNumber(accNumber: String) : Boolean {
        val regExp = Regex("^[0-9]{9,18}$")
        var isvalid = false
        if (accNumber.length > 0) {
            isvalid = accNumber.matches(regExp)
        }
        return isvalid
    }

    fun isIfscCodeValid(ifsc: String): Boolean {
        val regExp = Regex("^[A-Z]{4}[0][A-Z0-9]{6}$")
        var isvalid = false
        if (ifsc.length > 0) {
            isvalid = ifsc.matches(regExp)
        }
        return isvalid
    }

    fun isPan(pan: String): Boolean {
        val regExp = Regex("^[A-Z]{5}[0-9]{4}[A-Z]{1}$")
        var isvalid = false
        if (pan.length > 0) {
            isvalid = pan.matches(regExp)
        }
        return isvalid
    }

    fun isTan(tan: String): Boolean {
        val regExp = Regex("^[A-Z]{4}[0-9]{5}[A-Z]{1}$")
        var isvalid = false
        if (tan.length > 0) {
            isvalid = tan.matches(regExp)
        }
        return isvalid
    }

    fun isGst(gst: String): Boolean {
        val regExp = Regex("^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$")
        var isvalid = false
        if (gst.length > 0) {
            isvalid = gst.matches(regExp)
        }
        return isvalid
    }

    fun isLicense(tan: String): Boolean {
        val regExp = Regex("^(([A-Z]{2}[0-9]{2})( )|([A-Z]{2}-[0-9]{2})|([A-Z]{2}/[0-9]{2}))((19|20)[0-9][0-9])[0-9]{7}$")
        var isvalid = false
        if (tan.length > 0) {
            isvalid = tan.matches(regExp)
        }
        return isvalid
    }

    fun isValidCreditCard(txt: String): Boolean {
        return txt.length == 16
    }

    fun isValidCvv(cvv: String): Boolean {
        return cvv.length == 3
    }

    fun isAadhaar(aadhaar: String?): Boolean {
        val regExp = Regex("^[2-9]{1}[0-9]{3}\\s[0-9]{4}\\s[0-9]{4}$")
        var isvalid = false
        if (aadhaar!!.length > 0) {
            isvalid = aadhaar.matches(regExp)
        }
        return isvalid
    }

    fun isValidPincode(pincode: String?): Boolean {
        val regExp = Regex("^[1-9][0-9]{5}$")
        var isvalid = false
        if (pincode!!.length > 0) {
            isvalid = pincode.matches(regExp)
        }
        return isvalid
    }

    fun isValidOtp(otp: String): Boolean {
        return otp.length <= 6
    }

    fun isEmpty(list: List<Any>?): Boolean {
        return list == null || list.isEmpty()
    }

    fun isEmpty(list: Array<String>?): Boolean {
        return list == null || list.isEmpty()
    }

    fun SplitDate(date: String): Array<String> {
        val items1 = date.split("T".toRegex()).toTypedArray()
        return items1
    }
    fun SplitSpaceValue(date: String): Array<String> {
        val items1 = date.split(" ".toRegex()).toTypedArray()
        return items1
    }
    fun SplitHypen(date: String): Array<String> {
        val items1 = date.split("-".toRegex()).toTypedArray()
        return items1
    }
    fun SplitSlaceValue(date: String): Array<String> {
        val items1 = date.split("/".toRegex()).toTypedArray()
        return items1
    }

    fun SplitColon(date: String): Array<String> {
        val items1 = date.split(":".toRegex()).toTypedArray()
        return items1
    }

    fun getMonthByNumber(monthnum:Int):String {
        val c = Calendar.getInstance()
        val month_date = SimpleDateFormat("MMM")
        c[Calendar.MONTH] = monthnum-1
        return month_date.format(c.time)
    }

    fun SplitAdress(adress: String): String {
       /* val startAddress = routeListData.get(position).startlocation
        val arr = startAddress.split(" ".toRegex(), 2).toTypedArray()
        val startLoc = arr[0]*/
        val arr = adress.split(",".toRegex(), 2).toTypedArray()
        return arr[0]
    }

    fun splitSpaceValue(value:String):String{
        val arr = value.split(" ")
        return arr[0]
    }

    fun changeDateFormat(
        currentFormat: String,
        requiredFormat: String,
        dateString: String
    ): String {
        var result = ""
        val formatterOld = SimpleDateFormat(currentFormat, Locale.getDefault())
        val formatterNew = SimpleDateFormat(requiredFormat, Locale.getDefault())
        var date: Date? = null
        try {
            date = formatterOld.parse(dateString)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        if (date != null) {
            result = formatterNew.format(date)
        }
        return result
    }

    fun splitDotLatLangValue(value:String):Double{
        //var arr = value.split(".")
        var arr= value.replace(".","");
       /* Log.v("jhj",arr[0])
        Log.v("jhj",arr[1])

        var jointValue=arr[0]+"_"+arr[1]
        Log.v("jhj",jointValue)*/

       /* val char = '.'
        val index = 2*/
     //   var jointValue = jointValue.substring(0, index) + char + jointValue.substring(index + 1)
        var jointValue = arr.addCharAtIndex('.', 2)
        return jointValue.toDouble()
    }

    fun String.addCharAtIndex(char: Char, index: Int) =
        StringBuilder(this).apply { insert(index, char) }.toString()

    fun convertTime(input: Int): String {
        return if (input >= 10) {
            input.toString()
        } else {
            "0$input"
        }
    }

    fun getAddress(context: Context, lat: Double, lng: Double): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            val addresses: List<Address>? = geocoder.getFromLocation(lat, lng, 1)
            val obj: Address = addresses!![0]
            val countryName= obj.countryName
            val city= obj.locality  // chennai
            val area= obj.subLocality  // nandnam
            val address = obj.getAddressLine(0)
            val address1 = obj.getAddressLine(1)
            val address3 = obj.getAddressLine(1)
            val address4 = obj.getAddressLine(1)
            val address5 = obj.getAddressLine(1)

            return address+","/*+address1+","*/+city
        }
        catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
           // Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
        }
        return ""
    }

    fun getPincode(context: Context, lat: Double, lng: Double): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            val addresses: List<Address>? = geocoder.getFromLocation(lat, lng, 1)
            val obj: Address = addresses!![0]
            val pincode= obj.postalCode


            return pincode
        }
        catch (e: IOException) {
            e.printStackTrace()
            // Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
        }
        return ""
    }

    fun View.animateVisibility(setVisible: Boolean) {
        if (setVisible) expand(this) else collapse(this)
    }

    fun expand(view: View) {
        view.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val initialHeight = 0
        val targetHeight = view.measuredHeight

        // Older versions of Android (pre API 21) cancel animations for views with a height of 0.
        //v.getLayoutParams().height = 1;
        view.layoutParams.height = 0
        view.visibility = View.VISIBLE

        animateView(view, initialHeight, targetHeight)
    }

    fun collapse(view: View) {
        val initialHeight = view.measuredHeight
        val targetHeight = 0

        animateView(view, initialHeight, targetHeight)
    }

    private fun animateView(v: View, initialHeight: Int, targetHeight: Int) {
        val valueAnimator = ValueAnimator.ofInt(initialHeight, targetHeight)
        valueAnimator.addUpdateListener { animation ->
            v.layoutParams.height = animation.animatedValue as Int
            v.requestLayout()
        }
        valueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(animation: Animator) {
                v.layoutParams.height = targetHeight
            }

            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        valueAnimator.duration = 300
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.start()
    }

    fun SplitChargePointValue(chargePointId: String):String {

        val arr = chargePointId.split(";".toRegex(), 2).toTypedArray()
        val colonValue=Splitcolon(arr[1])
        return colonValue
    }

    fun Splitcolon(chargePointId: String):String {

        val arr = chargePointId.split(":".toRegex(), 2).toTypedArray()
        val colonValue=
            return arr[1]
    }









}