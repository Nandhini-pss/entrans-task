package com.example.testapps.services

import com.vital.radicube.utils.AppConstants


object ApiFactory {

    var RcApi: ApiInterface =
        RetrofitFactory.retrofit(AppConstants.BASE_URL).create(ApiInterface::class.java)
}


