package com.example.aplikasigithubuser.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.aplikasigithubuser.R
import com.example.aplikasigithubuser.database.SettingPreferences
import com.example.aplikasigithubuser.database.dataStore
import com.example.aplikasigithubuser.ui.viewModel.ThemeViewModel
import com.example.aplikasigithubuser.ui.viewModel.ViewModelFactory
import com.google.android.material.switchmaterial.SwitchMaterial

class ThemeActivity : AppCompatActivity() {

    private lateinit var switchTheme: SwitchMaterial
    private lateinit var themeViewModel: ThemeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme)

        switchTheme = findViewById(R.id.switch_theme)

        val pref = SettingPreferences.getInstance(application.dataStore)
        themeViewModel = ViewModelProvider(
            this,
            ViewModelFactory(pref)
        )[ThemeViewModel::class.java]

        setupThemeSwitch()
    }

    private fun setupThemeSwitch() {
        themeViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            setAppTheme(isDarkModeActive)
            switchTheme.isChecked = isDarkModeActive
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            themeViewModel.saveThemeSetting(isChecked)
        }
    }

    private fun setAppTheme(isDarkModeActive: Boolean) {
        val mode = if (isDarkModeActive) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }
        AppCompatDelegate.setDefaultNightMode(mode)
    }
}
