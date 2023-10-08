package com.example.aplikasigithubuser.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.aplikasigithubuser.database.SettingPreferences
import com.example.aplikasigithubuser.database.dataStore
import com.example.aplikasigithubuser.databinding.ActivitySplashScreenBinding
import com.example.aplikasigithubuser.ui.viewModel.MainViewModel
import com.example.aplikasigithubuser.ui.viewModel.MainViewModelFactory
import com.example.aplikasigithubuser.ui.viewModel.SpalshViewModel
import com.example.aplikasigithubuser.ui.viewModel.SplashViewModelFactory

class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var viewModel: SpalshViewModel

    private val splashTimeOut: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setViewModel()
        darkModeCheck()


        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler().postDelayed({

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, splashTimeOut)
    }

    private fun setViewModel(){
        val pref = SettingPreferences.getInstance(dataStore)
        viewModel = ViewModelProvider(this, SplashViewModelFactory(pref))[SpalshViewModel::class.java]
    }
    private fun darkModeCheck(){
        viewModel.getThemeSettings().observe(this@SplashScreen,{isDarkModeActive ->
            if (isDarkModeActive) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        })

    }
}