package com.vital.radicube.utils

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

const val PREFS_FILENAME = "radicubeappstateencript"
const val USER_ID= "USER_ID"
const val RC_ACCESSS_TOKEN = "RC_ACCESSS_TOKEN"
const val RC_REFRESH_TOKEN= "RC_REFRESH_TOKEN"
const val EMAIL_ID="EMAIL_ID"
const val PROFILE_IMAGE="PROFILE_IMAGE"
const val FIRST_NAME="FIRST_NAME"
const val LAST_NAME="LAST_NAME"
const val ROLE_NAME="ROLE_NAME"
const val STUDY="STUDY"
const val MOBILE="MOBILE"
const val ID="ID"
const val ACCEPTCOUNT="ACCEPTCOUNT"
const val PENDINGCOUNT="PENDINGCOUNT"



class VitalSharedPrefs private constructor(){

    companion object {
        private var INSTANCE: VitalSharedPrefs? = null
        //    private var encriptar:String?=null
        lateinit var prefs: SharedPreferences
        @RequiresApi(Build.VERSION_CODES.M)
        fun getInstance(context: Context): VitalSharedPrefs {

            val encriptar = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()
            //  encriptar = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
            return INSTANCE ?: VitalSharedPrefs().also {
                INSTANCE = it

                prefs = EncryptedSharedPreferences.create(context,PREFS_FILENAME, encriptar,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)
            }
        }
    }


    var userid: String?
        get() = prefs.getString(USER_ID, "")
        set(value: String?) = prefs.edit().putString(USER_ID, value!!).apply()

    var rc_Accesstoken: String?
        get() = prefs.getString(RC_ACCESSS_TOKEN, "")
        set(value: String?) = prefs.edit().putString(RC_ACCESSS_TOKEN, value!!).apply()
    var rc_refreshtoken: String?
        get() = prefs.getString(RC_REFRESH_TOKEN, "")
        set(value: String?) = prefs.edit().putString(RC_REFRESH_TOKEN, value!!).apply()
    var email:String?
        get() = prefs.getString(EMAIL_ID, "")
        set(value: String?) = prefs.edit().putString(EMAIL_ID, value!!).apply()
    var profileimage:String?
        get() = prefs.getString(PROFILE_IMAGE, "")
        set(value: String?) = prefs.edit().putString(PROFILE_IMAGE, value!!).apply()
    var firstname:String?
        get() = prefs.getString(FIRST_NAME, "")
        set(value: String?) = prefs.edit().putString(FIRST_NAME, value!!).apply()
    var lastname:String?
        get() = prefs.getString(LAST_NAME, "")
        set(value: String?) = prefs.edit().putString(LAST_NAME, value!!).apply()
    var rolename:String?
        get() = prefs.getString(ROLE_NAME, "")
        set(value: String?) = prefs.edit().putString(ROLE_NAME, value!!).apply()
    var study:String?
        get() = prefs.getString(STUDY, "")
        set(value: String?) = prefs.edit().putString(STUDY, value!!).apply()
    var mobile:String?
        get() = prefs.getString(MOBILE, "")
        set(value: String?) = prefs.edit().putString(MOBILE, value!!).apply()
    var id:String?
        get() = prefs.getString(ID, "")
        set(value: String?) = prefs.edit().putString(ID, value!!).apply()

}