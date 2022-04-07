package com.inferno.mobile.bedon_waseet.repos

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.database.getStringOrNull
import androidx.lifecycle.MutableLiveData
import com.inferno.mobile.bedon_waseet.BaseApplication
import com.inferno.mobile.bedon_waseet.responses.AvatarResult
import com.inferno.mobile.bedon_waseet.responses.Response
import com.inferno.mobile.bedon_waseet.responses.SignResponse
import com.inferno.mobile.bedon_waseet.utils.UserType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object SignUpRepo {
    private const val TAG: String = "SignUpRepo"

    //    fun register(query: String): MutableLiveData<Response> {
//        val liveData = MutableLiveData<Response>()
//        BaseApplication.service.register(query).enqueue(object : Callback<SignResponse> {
//            override fun onResponse(
//                call: Call<SignResponse>,
//                response: retrofit2.Response<SignResponse>
//            ) {
//                val rr = response.body()
//                if (rr == null) {
//                    Log.e(TAG, "onResponse : #${response.code()}")
//                } else
//                    liveData.value = rr.data.register
//            }
//
//            override fun onFailure(call: Call<SignResponse>, t: Throwable) {
//                Log.e(TAG, "onFailure", t)
//            }
//
//        })
//        return liveData
//    }
    fun register(
        username: String,
        password: String,
        email: String,
        type: UserType
    ): MutableLiveData<Response> {
        val liveData = MutableLiveData<Response>()
        val thread = Thread {
            Thread.sleep(3 * 1000) // 3 seconds
            liveData.postValue(
                Response(
                    code = 200,
                    message = "Account Created successfully",
                    data = "null",
                    type
                )
            )
        }
        thread.start()
        return liveData
    }

    fun uploadAvatar(context: Context, uri: Uri): MutableLiveData<AvatarResult> {
        val liveData = MutableLiveData<AvatarResult>()
        val file = File(getPath(context, uri))
        val requestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull()!!, file)
        val fileName = System.currentTimeMillis().toString() + ".${file.extension}"
        println(fileName)
        Toast.makeText(context, fileName, Toast.LENGTH_LONG).show()
        val body: MultipartBody.Part =
            MultipartBody.Part.createFormData("avatar", fileName, requestBody)
        BaseApplication.uploadService.uploadAvatar(body)
            .enqueue(object : Callback<AvatarResult> {
                override fun onResponse(
                    call: Call<AvatarResult>,
                    response: retrofit2.Response<AvatarResult>
                ) {
                    if (response.isSuccessful && response.body() != null)
                        liveData.value = response.body()
                    else Log.e(TAG, "uploadService\$onResponse #${response.code()}")
                }

                override fun onFailure(call: Call<AvatarResult>, t: Throwable) {
                    Log.e(TAG, "onFailure", t)
                }
            })
        return liveData
    }

    private fun getFile(context: Context, contentUri: Uri): File? {
        return try {
            val bitmap =
                MediaStore.Images.Media.getBitmap(context.contentResolver, contentUri)
            val imageType: String = context.contentResolver.getType(contentUri)!!
            val extension = imageType.substring(imageType.indexOf("/") + 1)
            val f = File(context.cacheDir, "filename.$extension")
            f.createNewFile()
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 75, bos)
            val bitmapData = bos.toByteArray()
            val fos = FileOutputStream(f)
            fos.write(bitmapData)
            fos.flush()
            fos.close()
            f
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun getPath(context: Context, uri: Uri): String {
        val file = getFile(context, uri)
        return if (file != null)
            file.path
        else ""
    }

}