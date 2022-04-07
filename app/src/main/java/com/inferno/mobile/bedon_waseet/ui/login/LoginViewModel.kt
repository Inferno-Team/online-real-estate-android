package com.inferno.mobile.bedon_waseet.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.inferno.mobile.bedon_waseet.repos.LoginRepo
import com.inferno.mobile.bedon_waseet.responses.Response

class LoginViewModel : ViewModel() {
    private val repo = LoginRepo

    fun login(email: String,password: String): LiveData<Response> {
        return repo.login(email,password)
    }

}