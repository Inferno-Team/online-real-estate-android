package com.inferno.mobile.bedon_waseet.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.inferno.graphql.GraphqlQuery
import com.inferno.graphql.QueryParameter
import com.inferno.mobile.bedon_waseet.activities.MainActivity
import com.inferno.mobile.bedon_waseet.activities.SplashActivity
import com.inferno.mobile.bedon_waseet.databinding.LoginFragmentBinding
import com.inferno.mobile.bedon_waseet.responses.Response
import com.inferno.mobile.bedon_waseet.utils.UserType
import com.inferno.mobile.bedon_waseet.utils.logIn

class LoginFragment : Fragment() {
    private val TAG = "LoginFragment"
    private lateinit var binding: LoginFragmentBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        binding.loginBtn.setOnClickListener {
            val email = binding.emailFiled.editableText.toString()
            val password = binding.passwordFiled.editableText.toString()
            val query = createLoginQuery(email, password)
            viewModel.login(email,password).observe(requireActivity(), this::onLoginResponse)

        }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finishAffinity()
                }
            })
    }

    private fun onLoginResponse(response: Response) {
        if (response.code == 200 && response.data != null) {
            logIn(requireContext(), response.data, response.type)
        }
        Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
        Log.d(TAG, "onLoginResponse #${response.code}")
        val _intent = Intent(requireActivity(), SplashActivity::class.java)
        requireActivity().finish()
        startActivity(_intent)
    }

    private fun createLoginQuery(email: String, password: String): String {
        return GraphqlQuery.Builder()
            .startQuery()
            .addQuery(
                queryName = "response:login", selectedFields = arrayOf("msg", "code", "data"),
                args = arrayOf(
                    QueryParameter(key = "email", value = email),
                    QueryParameter(key = "password", value = password)
                )
            )
            .end()
            .build()
    }
}