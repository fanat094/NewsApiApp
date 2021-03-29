package com.dyamschikov.newsapiapp.utils

import androidx.appcompat.app.AppCompatDelegate

object ThemeManager {
    private const val LIGHT_MODE = "Light"
    private const val DARK_MODE = "Dark"
    private const val FOLLOW_SYSTEM_MODE = "System"

    fun applyTheme(themePreference: String) {
        when (themePreference) {
            LIGHT_MODE -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            DARK_MODE -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            FOLLOW_SYSTEM_MODE -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}