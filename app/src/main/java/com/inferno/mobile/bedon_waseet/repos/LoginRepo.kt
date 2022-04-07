package com.inferno.mobile.bedon_waseet.repos

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.inferno.mobile.bedon_waseet.BaseApplication
import com.inferno.mobile.bedon_waseet.responses.Response
import com.inferno.mobile.bedon_waseet.responses.SignResponse
import com.inferno.mobile.bedon_waseet.utils.UserType
import retrofit2.Call
import retrofit2.Callback
import java.util.*

object LoginRepo {
    private const val TAG = "LoginRepo"

    //    fun login(query:String):MutableLiveData<Response>{
//        val liveData = MutableLiveData<Response>()
//        BaseApplication.service.login(query)
//            .enqueue(object:Callback<SignResponse>{
//                override fun onResponse(
//                    call: Call<SignResponse>,
//                    response: retrofit2.Response<SignResponse>
//                ) {
//                    val rr = response.body()
//                    if (rr == null) {
//                        Log.e(TAG, "onResponse : #${response.code()}")
//                    } else
//                        liveData.value = rr.data.register
//                }
//
//                override fun onFailure(call: Call<SignResponse>, t: Throwable) {
//                    Log.e(TAG, "onFailure", t)
//                }
//
//            })
//        return liveData
//    }
    fun login(email: String, password: String): MutableLiveData<Response> {
        var userType = UserType.Gust
        when {
            email.lowercase(Locale.getDefault()).contains("owner") -> {
                userType = UserType.Owner
            }
            email.lowercase(Locale.getDefault()).contains("admin") -> {
                userType = UserType.Admin
            }
            email.lowercase(Locale.getDefault()).contains("gust") -> {
                userType = UserType.Gust
            }
            email.lowercase(Locale.getDefault()).contains("customer") -> {
                userType = UserType.Customer
            }
        }

        val liveData = MutableLiveData<Response>()
        val thread = Thread {
            Thread.sleep(3 * 1000) // 3 seconds
            liveData.postValue(
                Response(
                    code = 200,
                    message = "Logged in successfully",
                    data = "token code",
                    userType
                )
            )
        }
        thread.start()
        return liveData
    }
}