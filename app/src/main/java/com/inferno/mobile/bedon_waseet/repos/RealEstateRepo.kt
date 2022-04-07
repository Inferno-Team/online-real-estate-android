package com.inferno.mobile.bedon_waseet.repos

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.inferno.mobile.bedon_waseet.BaseApplication
import com.inferno.mobile.bedon_waseet.responses.RealEstate
import com.inferno.mobile.bedon_waseet.responses.RealEstateResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RealEstateRepo {
    private const val TAG = "RealEstateRepo"

    //    fun getAllRealEstate(query: String): MutableLiveData<ArrayList<RealEstate>> {
//        val liveData = MutableLiveData<ArrayList<RealEstate>>()
//        BaseApplication.service.getAllRealEstate(query)
//            .enqueue(object : Callback<RealEstateResponse> {
//                override fun onResponse(
//                    call: Call<RealEstateResponse>,
//                    response: Response<RealEstateResponse>
//                ) {
//                    if (response.isSuccessful && response.body() != null)
//                        liveData.value = response.body()!!.data.states
//                    else Log.e(TAG, "getAllRealEstate\$onResponse ${response.code()}")
//                }
//
//                override fun onFailure(call: Call<RealEstateResponse>, t: Throwable) {
//                    Log.e(TAG, "onFailure", t)
//                }
//
//            })
//        return liveData
//    }
    fun getAllRealEstate(query: String): MutableLiveData<ArrayList<RealEstate>> {
        val liveData = MutableLiveData<ArrayList<RealEstate>>()
        val thread = Thread {
            Thread.sleep(3 * 1000) // 3 seconds
            val list = getFakeRealEstate()
            liveData.postValue(list)
        }
        thread.start()
        return liveData
    }

    private fun getFakeRealEstate(): ArrayList<RealEstate> {
        val list = ArrayList<RealEstate>()
        for (i: Int in 0..10) {
            list.add(
                RealEstate(
                    id = i,
                    location = "Aleppo",
                    type = "Home",
                    img_url = "",
                    buy_type = "Buy",
                    budget = 1e7F,
                    lat = 0F,
                    log = 0F,
                    rate = 4.2F,
                    video = "",
                    user = null,
                    rooms = listOf()
                )
            )
        }
        return list
    }
}