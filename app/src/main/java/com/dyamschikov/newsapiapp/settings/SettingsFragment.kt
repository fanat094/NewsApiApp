package com.dyamschikov.newsapiapp.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.dyamschikov.newsapiapp.R
import com.dyamschikov.newsapiapp.utils.SharedPreferenceHelper

class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var sharedPreferenceHelper: SharedPreferenceHelper
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        sharedPreferenceHelper = SharedPreferenceHelper.getInstance(requireContext())
        init()
    }

    private fun init() {
        val themePreferenceKey = PREFERENCE_KEY_THEME
        val themePreference = findPreference<Preference>(themePreferenceKey)
        val selectedOption = sharedPreferenceHelper.getSelectedThemePref()
        themePreference?.summary = selectedOption
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        val themePreferenceKey = PREFERENCE_KEY_THEME
        if (key == themePreferenceKey) {
            val themePreference = findPreference<Preference>(themePreferenceKey)
            val selectedOption = sharedPreferenceHelper.getSelectedThemePref()
            themePreference?.summary = selectedOption

            when(selectedOption){
                getString(R.string.follow_system_value) -> setTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                getString(R.string.light_theme_value) -> setTheme(AppCompatDelegate.MODE_NIGHT_NO)
                getString(R.string.dark_theme_value) -> setTheme(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }

    private fun setTheme(mode: Int) {
        AppCompatDelegate.setDefaultNightMode(mode)
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    companion object {
        private const val PREFERENCE_KEY_THEME = "theme_key"
    }
}