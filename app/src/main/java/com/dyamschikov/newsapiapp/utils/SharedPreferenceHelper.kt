package com.dyamschikov.newsapiapp.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class SharedPreferenceHelper {

    companion object {
        private var prefs: SharedPreferences? = null
        private var instance: SharedPreferenceHelper? = null
        private const val PREFERENCE_KEY_THEME = "theme_key"

        fun getInstance(context: Context): SharedPreferenceHelper {
            if (instance == null) {
                prefs = PreferenceManager.getDefaultSharedPreferences(context)
            }
            return SharedPreferenceHelper()
        }
    }

    fun getSelectedThemePref() = prefs?.getString(PREFERENCE_KEY_THEME, "")
}