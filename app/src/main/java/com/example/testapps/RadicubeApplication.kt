package com.example.testapps
import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.vital.radicube.utils.VitalSharedPrefs

class RadicubeApplication: Application() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate() {
        super.onCreate()
        application = this
        publicPrefs = VitalSharedPrefs.getInstance(applicationContext!!)

    }

    companion object{
         var publicPrefs: VitalSharedPrefs?=null
         var application: RadicubeApplication?=null
            private set
    }

}