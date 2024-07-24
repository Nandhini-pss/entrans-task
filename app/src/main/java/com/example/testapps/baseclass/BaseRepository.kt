package com.example.testapps.baseclass

import android.accounts.NetworkErrorException
import android.util.Log
import com.example.testapps.pojoclass.ApiErrorResponse
import com.example.testapps.pojoclass.ApiResponse
import okhttp3.internal.http2.StreamResetException
import org.json.JSONObject
import retrofit2.Response
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException


open class BaseRepository {

    suspend fun <T : Any> doApiCall(call: suspend () -> Response<T>): ApiResponse<T>? {

        var apiResponse : ApiResponse<T> = ApiResponse<T>()
        var data : T? = null

        try {
            val result : Result<T, ApiErrorResponse> = onApiResult(call)

            when(result) {
                is Result.Success -> {
                    data = result.data
                    apiResponse.data = data
                    apiResponse.error = false

                }
                is Result.ApiError -> {
                    var apiErrorResponse: ApiErrorResponse = result.data
                    apiResponse.msg = apiErrorResponse.errMsg
                    apiResponse.error = true
                }
                is Result.Error -> {
                    //Log.d("1.DataRepository", "!!! Exception - ${result.exception}")
                    apiResponse.error = true
                    apiResponse.msg = "Unknown Error"
                }
                is Result.LoginRedirect -> {
                    apiResponse.error = true
                    apiResponse.msg = result.msg
                    apiResponse.isRedirect = true

                    if(apiResponse.isRedirect){  // if user blocked means need to redirect login page
                    }
                }
            }
            return apiResponse
        }
        catch (unknownHostException : UnknownHostException){
            // Log.e("message ","message "+unknownHostException.message)
            apiResponse.error = true
            apiResponse.msg = unknownHostException.message
            return apiResponse
        }
        catch (sslHandshakeException : SSLHandshakeException){
            //Log.e("message ","message "+sslHandshakeException.message)
            apiResponse.error = true
            apiResponse.msg = sslHandshakeException.message
            return apiResponse
        }

        catch (networkException : NetworkErrorException){
            //Log.e("message ","message "+networkException.message)
            apiResponse.error = true
            apiResponse.msg = networkException.message
            return apiResponse
        }

        catch (streamResetException : StreamResetException){
            //Log.e("message ","message "+streamResetException.message)
            apiResponse.error = true
            apiResponse.msg = streamResetException.message
            return apiResponse
        }

        catch (socketTimeoutException : SocketTimeoutException){
            //Log.e("message ","message "+socketTimeoutException.message)
            apiResponse.error = true
            apiResponse.msg = socketTimeoutException.message
            return apiResponse
        }

        catch (connectException : ConnectException){
            //Log.e("message ","message "+socketTimeoutException.message)
            apiResponse.error = true
            apiResponse.msg = connectException.message
            return apiResponse
        }


    }

    private suspend fun <T : Any, U : Any> onApiResult(call: suspend () -> Response<T>): Result<T, U> {
        val response = call.invoke()
        if (response.isSuccessful) {
            return Result.Success(response.body()!!)
        } else {
            var msg = ""
            var isBlocked = false
            var reader: BufferedReader? = null
            val sb = StringBuilder()
            try {
                reader = BufferedReader(InputStreamReader(response.errorBody()!!.byteStream()))
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    sb.append(line)
                }
                val errorBody = sb.toString()

//                Log.d("BBBBB>>>>>>", errorBody)
                val j = JSONObject(errorBody)
                msg = j.optString("msg", response.message())
                isBlocked = j.optBoolean("blocked", false)

            } catch (e: Exception) {

                Log.e("***API PARSE ERR****", e.toString())
                msg = response.message()
            }
            if (response.code() == 403 && isBlocked) {
                Log.d("** BLOCKED **", "Account is blocked")
                return Result.LoginRedirect(msg)
            } else {
                val data: U = ApiErrorResponse(msg) as U
                return Result.ApiError(data)
            }
        }
    }
}