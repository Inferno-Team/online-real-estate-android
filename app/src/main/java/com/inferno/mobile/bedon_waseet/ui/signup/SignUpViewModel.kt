package com.inferno.mobile.bedon_waseet.ui.signup

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.inferno.mobile.bedon_waseet.repos.SignUpRepo
import com.inferno.mobile.bedon_waseet.responses.AvatarResult
import com.inferno.mobile.bedon_waseet.responses.Response
import com.inferno.mobile.bedon_waseet.utils.UserType

class SignUpViewModel : ViewModel() {
    private var repo = SignUpRepo

    fun register(username:String, password:String, email:String, type: UserType): LiveData<Response> {
        return repo.register(username, password, email, type)
    }

    fun uploadAvatar(context:Context,uri:Uri):LiveData<AvatarResult>{
        return repo.uploadAvatar(context,uri)
    }
}
