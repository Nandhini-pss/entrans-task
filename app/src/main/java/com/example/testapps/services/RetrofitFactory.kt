package com.example.testapps.services

import com.example.testapps.RadicubeApplication
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.vital.radicube.utils.AppConstants
import kotlinx.coroutines.runBlocking
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitFactory {

    private val newAuthInterceptor = Interceptor { chain ->
        synchronized(this) {
            val keepAliveUrl = AppConstants.BASE_URL
            var tkn: String? = RadicubeApplication.publicPrefs?.rc_Accesstoken
            var tkn_ref: String? = RadicubeApplication.publicPrefs?.rc_refreshtoken

            val originalRequest = chain.request()

            when {
                originalRequest.header("No-Authentication") == null -> {
                    val authenticationRequest = originalRequest.newBuilder()
                        .addHeader("Authorization", "Bearer ${tkn}")
                        .build()

                    val initialResponse = chain.proceed(authenticationRequest)

                    when {
                        initialResponse.code == 401 -> {
                            //RUN BLOCKING!!
                            val resp = runBlocking {
                                val jsonString = "{\"tkn\":\"${tkn_ref}\"}"
                                var postBody: RequestBody = RequestBody.create(
                                    "application/json; charset=utf-8".toMediaTypeOrNull(),
                                    jsonString
                                )

                                var refreshtokenRequest =
                                    chain.request().newBuilder().post(postBody).url(keepAliveUrl)
                                        .build()
                                chain.proceed(refreshtokenRequest)
                            }

                            when {
                                resp == null || resp.code != 200 -> {
                                    resp
                                }

                                else -> {
                                    val newresp = resp.body?.string()
                                    val jsonObj = JSONObject(newresp)
                                    tkn = jsonObj.optString("tkn", null)
                                    RadicubeApplication.publicPrefs?.rc_Accesstoken = tkn
                                    val newAuthenticationRequest =
                                        originalRequest.newBuilder().addHeader(
                                            "Authorization",
                                            "Bearer $tkn"
                                        ).build()
                                    chain.proceed(newAuthenticationRequest)
                                }
                            }
                        }

                        else -> initialResponse
                    }
                }

                else -> chain.proceed(originalRequest)
            }
        }
    }


    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

//    private val client =
//        OkHttpClient().newBuilder()
//            .addInterceptor(authInterceptor)
//            .addInterceptor(loggingInterceptor)
//            .readTimeout(60, TimeUnit.SECONDS)
//            .connectTimeout(60, TimeUnit.SECONDS)
//            .build()

    private fun createClientAuth(): OkHttpClient {
        //ADD DISPATCHER WITH MAX REQUEST TO 1
        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 1
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        okHttpClientBuilder.dispatcher(dispatcher)
        okHttpClientBuilder.addInterceptor(newAuthInterceptor)
        okHttpClientBuilder.addInterceptor(loggingInterceptor)
        okHttpClientBuilder.readTimeout(120, TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(120, TimeUnit.SECONDS)
        okHttpClientBuilder.connectTimeout(120, TimeUnit.SECONDS)
        return okHttpClientBuilder.build()
    }


    fun retrofit(baseUrl: String): Retrofit = Retrofit.Builder()
        .client(createClientAuth())
        .baseUrl(baseUrl)
        //.addConverterFactory(MoshiConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
}