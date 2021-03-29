package com

import android.app.Application
import androidx.preference.PreferenceManager
import com.dyamschikov.newsapiapp.BuildConfig
import com.dyamschikov.newsapiapp.di.component.AppComponent
import com.dyamschikov.newsapiapp.di.component.DaggerAppComponent
import com.dyamschikov.newsapiapp.utils.ThemeManager
import com.facebook.stetho.Stetho
import timber.log.Timber

class BaseApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Stetho.initializeWithDefaults(this)
        initTheme()
    }

    private fun initTheme() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        runCatching {
            ThemeManager.applyTheme(requireNotNull(preferences.getString(PREFERENCE_KEY_THEME, "")))
        }.onFailure { exception ->
            Timber.e("$LABEL_THEME_PREFERENCE_ERROR $exception")
        }
    }

    companion object {
        private const val PREFERENCE_KEY_THEME = "theme_key"
        private const val LABEL_THEME_PREFERENCE_ERROR = "Theme Preference:"
    }
}