package org.sopt.dosopttemplate

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import org.sopt.dosopttemplate.data.datasource.local.AuthSharedPref
import timber.log.Timber

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        AuthSharedPref.initAuthPref(this)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

}