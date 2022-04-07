package com.inferno.mobile.bedon_waseet.services

import com.inferno.mobile.bedon_waseet.responses.AvatarResult
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface UploadService {
    @POST("/upload_avatar")
    @Multipart
    fun uploadAvatar(@Part avatar: MultipartBody.Part):Call<AvatarResult>
}