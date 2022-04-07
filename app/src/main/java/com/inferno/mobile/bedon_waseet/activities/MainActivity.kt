package com.inferno.mobile.bedon_waseet.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.inferno.mobile.bedon_waseet.R
import com.inferno.mobile.bedon_waseet.databinding.ActivityMainBinding
import com.inferno.mobile.bedon_waseet.utils.UserType
import com.inferno.mobile.bedon_waseet.utils.getUserType
import com.inferno.mobile.bedon_waseet.utils.isUserLoggedIn

class MainActivity : AppCompatActivity() {


    private lateinit var viewBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val route = intent.getStringExtra("route")
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        if (isUserLoggedIn(this)) {
            viewBinding.bottomNavView.visibility = View.VISIBLE
            inflateBottomMenu()
        } else {
            viewBinding.bottomNavView.visibility = View.GONE
        }

        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(viewBinding.root)
        val navController = findNavController(R.id.fragment_main)
        NavigationUI.setupWithNavController(viewBinding.bottomNavView, navController)

        when (route!!) {
            "home" -> {
            }
            "login" -> {
                navController.navigate(R.id.action_nav_home_to_loginFragment)
                inflateBottomMenu()
            }
            "signup" -> {
                navController.navigate(R.id.action_nav_home_to_signUpFragment)
                inflateBottomMenu()
            }
        }
    }

    /*
        override fun onBackPressed() {
            if (findNavController(R.id.fragment_main).currentDestination!!.id == R.id.signUpFragment ||
                findNavController(R.id.fragment_main).currentDestination!!.id == R.id.loginFragment
            ) finishAffinity()
            else super.onBackPressed()
        }*/
    private fun inflateBottomMenu() {
        val userType = getUserType(this)
        viewBinding.bottomNavView.menu.clear()
        if (userType != null) {
            if (userType == UserType.Owner) {
                viewBinding.bottomNavView.inflateMenu(R.menu.owner_nav_menu)
            } else
                viewBinding.bottomNavView.inflateMenu(R.menu.nav_menu)

        } else viewBinding.bottomNavView.inflateMenu(R.menu.nav_menu)
    }
}