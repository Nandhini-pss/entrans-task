package com.vital.radicube.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.provider.Settings.Secure
import android.util.Log
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit


fun Fragment.showSuccessToast(msg: String) {
    view?.let { activity?.showSuccessToast(msg) }
}

fun Activity.showSuccessToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Fragment.showMessageToast(msg: String) {
    view?.let { activity?.showMessageToast(msg) }
}

fun Activity.showMessageToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

@RequiresApi(Build.VERSION_CODES.O)
fun dob_date(value: String): String {
    val inputFormatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
    val outputFormatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern("dd-MM-yyy", Locale.ENGLISH)
    val date: LocalDate = LocalDate.parse(value, inputFormatter)
    val formattedDate: String = outputFormatter.format(date)
    println(formattedDate) // prints 10-04-2018
    return formattedDate
}

fun getMimeType(context: Context?, uri: Uri?): String? {
    var mimeType: String? = null
    mimeType = if (ContentResolver.SCHEME_CONTENT == uri?.getScheme()) {
        val s = context?.contentResolver?.getType(uri)
        return s

    } else {
        val fileExtension = MimeTypeMap.getFileExtensionFromUrl(
            uri
                .toString()
        )
        MimeTypeMap.getSingleton().getMimeTypeFromExtension(
            fileExtension.toLowerCase()
        )
    }
    return mimeType
}

fun covertTimeToText(dataDate: String?): String? {
    var convTime: String? = null
    val prefix = ""
    val suffix = "Ago"
    try {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val pasTime: Date = dateFormat.parse(dataDate)
        val nowTime = Date()
        val dateDiff = nowTime.time - pasTime.time
        val second: Long = TimeUnit.MILLISECONDS.toSeconds(dateDiff)
        val minute: Long = TimeUnit.MILLISECONDS.toMinutes(dateDiff)
        val hour: Long = TimeUnit.MILLISECONDS.toHours(dateDiff)
        val day: Long = TimeUnit.MILLISECONDS.toDays(dateDiff)
        if (second < 60) {
            convTime = "$second Seconds $suffix"
        } else if (minute < 60) {
            convTime = "$minute Minutes $suffix"
        } else if (hour < 24) {
            convTime = "$hour Hours $suffix"
        } else if (day >= 7) {
            convTime = if (day > 360) {
                (day / 360).toString() + " Years " + suffix
            } else if (day > 30) {
                (day / 30).toString() + " Months " + suffix
            } else {
                (day / 7).toString() + " Week " + suffix
            }
        } else if (day < 7) {
            convTime = "$day Days $suffix"
        }
    } catch (e: ParseException) {
        e.printStackTrace()
        Log.e("ConvTimeE", e.message.toString())
    }
    return convTime
}

fun requestPermission(
    activity: Activity?, permissions: Array<String?>?,
    REQUEST_CODE: Int
) {
    // No explanation needed, we can request the permission.
    ActivityCompat.requestPermissions(activity!!, permissions!!, REQUEST_CODE)
}

fun requesttPermission(
    activity: Activity?, permission: String,
    REQUEST_CODE: Int
) {
    // No explanation needed, we can request the permission.
    ActivityCompat.requestPermissions(activity!!, arrayOf(permission), REQUEST_CODE)
}

fun checkPermissonGranted(context: Context?, permission: String?): Boolean {
    return (ActivityCompat.checkSelfPermission(context!!, permission!!)
            == PackageManager.PERMISSION_GRANTED)
}

@SuppressLint("HardwareIds")
fun getDeviceUniqueID(activity: Activity): String? {
    return Secure.getString(
        activity.contentResolver,
        Secure.ANDROID_ID
    )
}

fun getOnlyDateFromDate(strDate: String): String {
    //current date format
    // val dateFormaqt = SimpleDateFormat("yyyy-MM-dd");
    val dateFormaqt = SimpleDateFormat("dd/MM/yyyy");
    val objDate = dateFormaqt.parse(strDate);

    //Expected date format
    val dateFormat2 = SimpleDateFormat("yyyy-MM-dd");
    //val dateFormat2 =  SimpleDateFormat("dd");

    val finalDate = dateFormat2.format(objDate)
    return finalDate
}

fun getOnlyMonthFromDate(strDate: String): String {
    //current date format
    //val dateFormaqt = SimpleDateFormat("yyyy/MM/dd");
    val dateFormaqt = SimpleDateFormat("dd/MM/yyyy");

    val objDate = dateFormaqt.parse(strDate);

    //Expected date format
    // val dateFormat2 =  SimpleDateFormat("dd-MM-yyyy");
    val dateFormat2 = SimpleDateFormat("MM");

    val finalDate = dateFormat2.format(objDate)
    return finalDate
}

fun getOnlyyearFromDate(strDate: String): String {
    //current date format
    // val dateFormaqt = SimpleDateFormat("yyyy-MM-dd");
    val dateFormaqt = SimpleDateFormat("dd/MM/yyyy");

    val objDate = dateFormaqt.parse(strDate);

    //Expected date format
    // val dateFormat2 =  SimpleDateFormat("dd-MM-yyyy");
    val dateFormat2 = SimpleDateFormat("yyyy");

    val finalDate = dateFormat2.format(objDate)
    return finalDate
}

fun getOnlyDateFromDateFilter(strDate: String): String {
    //current date format
    val dateFormaqt = SimpleDateFormat("yyyy-MM-dd");
    //val dateFormaqt = SimpleDateFormat("dd/MM/yyyy");
    val objDate = dateFormaqt.parse(strDate);

    //Expected date format
    // val dateFormat2 =  SimpleDateFormat("dd-MM-yyyy");
    val dateFormat2 = SimpleDateFormat("dd");

    val finalDate = dateFormat2.format(objDate)
    return finalDate
}

fun getOnlyMonthFromDateFilter(strDate: String): String {
    //current date format
    val dateFormaqt = SimpleDateFormat("yyyy-MM-dd");
    //val dateFormaqt = SimpleDateFormat("dd/MM/yyyy");

    val objDate = dateFormaqt.parse(strDate);

    //Expected date format
    // val dateFormat2 =  SimpleDateFormat("dd-MM-yyyy");
    val dateFormat2 = SimpleDateFormat("MM");

    val finalDate = dateFormat2.format(objDate)
    return finalDate
}

fun getOnlyyearFromDateFilter(strDate: String): String {
    //current date format
    val dateFormaqt = SimpleDateFormat("yyyy-MM-dd");
    // val dateFormaqt = SimpleDateFormat("dd/MM/yyyy");

    val objDate = dateFormaqt.parse(strDate);

    //Expected date format
    // val dateFormat2 =  SimpleDateFormat("dd-MM-yyyy");
    val dateFormat2 = SimpleDateFormat("yyyy");

    val finalDate = dateFormat2.format(objDate)
    return finalDate
}

fun formatDatewithouttime(inputDate: String): String {
    // Define the input and output date formats
    println("inputDate " + inputDate)
    val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val outputDateFormat = SimpleDateFormat("dd-MMM-yyyy")

    // Parse the input date string into a Date object
    val date: Date? = inputDateFormat.parse(inputDate)

    // Format the Date object into the desired output format
    return date?.let { outputDateFormat.format(it) } ?: ""
}

// Format time in 24-hour format
fun formatTime24Hour(time: String): String {
    val simpleDateFormat12 = SimpleDateFormat("hh:mm a", Locale.getDefault())
    val cal = Calendar.getInstance()
    cal.time = simpleDateFormat12.parse(time) ?: Date()
    val simpleDateFormat24 = SimpleDateFormat("HH:mm", Locale.getDefault())
    return simpleDateFormat24.format(cal.time)
}

fun formatTime12Hour(timeString: String): String {
    val simpleDateFormat24 = SimpleDateFormat("HH:mm", Locale.getDefault())
    val simpleDateFormat12 = SimpleDateFormat("hh:mm a", Locale.getDefault())

    val time = simpleDateFormat24.parse(timeString)
    return simpleDateFormat12.format(time)
}


fun setworkingActivityDateFormate(inputDate: String): String {

    // Define the input and output date formats
    println("inputDate " + inputDate)
    val inputDateFormat = SimpleDateFormat("yyyy-MM-dd")
    val outputDateFormat = SimpleDateFormat("dd/MM/yyyy")

    // Parse the input date string into a Date object
    val date: Date? = inputDateFormat.parse(inputDate)

    // Format the Date object into the desired output format
    return date?.let { outputDateFormat.format(it) } ?: ""

}

fun setworkingActivityPeriodicallyDateFormate(inputDate: String): String {

    // Define the input and output date formats
    println("inputDate " + inputDate)
    val inputDateFormat = SimpleDateFormat("dd/MM/yyyy")
    val outputDateFormat = SimpleDateFormat("yyyy-MM-dd")

    // Parse the input date string into a Date object
    val date: Date? = inputDateFormat.parse(inputDate)

    // Format the Date object into the desired output format
    return date?.let { outputDateFormat.format(it) } ?: ""

}

fun setworkingActivityPeriodicallyDateFormateWhenEdit(inputDate: String): String {

    // Define the input and output date formats
    println("inputDate " + inputDate)
    val inputDateFormat = SimpleDateFormat("yyyy-MM-dd")
    val outputDateFormat = SimpleDateFormat("dd/MM/yyyy")

    // Parse the input date string into a Date object
    val date: Date? = inputDateFormat.parse(inputDate)

    // Format the Date object into the desired output format
    return date?.let { outputDateFormat.format(it) } ?: ""

}

fun convertMeridianTimeToUTC(meridianTime: String, meridianPattern: String = "hh:mm:ss a"): String {
    // Step 1: Create a SimpleDateFormat object for the input meridian time
    val meridianFormat = SimpleDateFormat(meridianPattern, Locale.getDefault())

    // Step 2: Parse the meridian time to obtain a Date object
    val date = meridianFormat.parse(meridianTime)

    // Step 3: Create a SimpleDateFormat object for the UTC time
    val utcFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

    // Step 4: Set the time zone of the UTC format to UTC
    utcFormat.timeZone = TimeZone.getTimeZone("UTC")

    // Step 5: Format the date to obtain the UTC time as a string
    return utcFormat.format(date)
}

fun addconvertDateFormat(dateString: String): String {

    return "${convertDateFormatForAcceptDate(dateString)}\n${
        convertDateFormatForAcceptTime(
            dateString
        )
    }"

}

fun convertDateFormatForAcceptDate(dateString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd/MMMyyyy", Locale.getDefault())
    val date = inputFormat.parse(dateString)
    return outputFormat.format(date)
}

fun convertDateFormatForAcceptTime(dateString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    val date = inputFormat.parse(dateString)
    return outputFormat.format(date)
}

fun isOnline(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val n = cm.activeNetwork
        if (n != null) {
            val nc = cm.getNetworkCapabilities(n)
            // It will check for both wifi and cellular network
            nc?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true ||
                    nc?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true
        } else {
            false
        }
    } else {
        @Suppress("DEPRECATION")
        val netInfo = cm.activeNetworkInfo
        netInfo != null && netInfo.isConnectedOrConnecting
    }
}

fun createDrawableColor(colorString: String): Drawable {
    val drawable = GradientDrawable()
    drawable.shape = GradientDrawable.RECTANGLE
    drawable.cornerRadius = 30f // Adjust the corner radius as needed
    drawable.setColor(Color.parseColor(colorString))
    return drawable
}

fun adjustImageViewSize(imageView: ImageView) {
    val params = imageView.layoutParams ?: ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
    params.width = 25 // Adjust the width as needed
    params.height = 25 // Adjust the height as needed
    imageView.layoutParams = params
}

fun getFormattedDate(calendar: Calendar): String {
    val month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
    return "$month"
}

fun getOrdinalSuffix(day: Int): String {
    return when (day % 10) {
        1 -> "st"
        2 -> "nd"
        3 -> "rd"
        else -> "th"
    }
}

