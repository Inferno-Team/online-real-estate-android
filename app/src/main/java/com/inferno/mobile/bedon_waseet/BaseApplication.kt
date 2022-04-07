package com.inferno.mobile.bedon_waseet

import android.app.Application
import com.inferno.mobile.bedon_waseet.services.GraphQLService
import com.inferno.mobile.bedon_waseet.services.UploadService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BaseApplication : Application() {

    companion object {
        const val BASE_URL = "http://192.168.43.113"

        // val BASE_URL = "http://192.168.1.4"
        private const val port = "3001"
        lateinit var service: GraphQLService
        lateinit var uploadService: UploadService
    }
    init {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("$BASE_URL:$port/")
            .build()
        service = retrofit.create(GraphQLService::class.java)
        uploadService = retrofit.create(UploadService::class.java)
    }
}