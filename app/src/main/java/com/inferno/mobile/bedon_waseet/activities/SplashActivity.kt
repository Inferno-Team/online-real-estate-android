package com.inferno.mobile.bedon_waseet.activities

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.inferno.mobile.bedon_waseet.databinding.SplashActivityBinding
import com.inferno.mobile.bedon_waseet.utils.isUserLoggedIn
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: SplashActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding = SplashActivityBinding.inflate(layoutInflater)
        val _intent = Intent(this, MainActivity::class.java)
        if (isUserLoggedIn(this)) {
//            intent = Intent(this, VRActivity::class.java)
//            startActivity(intent)
            _intent.putExtra("route", "home")
            startActivity(_intent)
            finish()
        }
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            _intent.putExtra("route", "login")
            startActivity(_intent)
            finish()
        }
        binding.signupButton.setOnClickListener {
            _intent.putExtra("route", "signup")
            startActivity(_intent)
            finish()
        }
    }
}