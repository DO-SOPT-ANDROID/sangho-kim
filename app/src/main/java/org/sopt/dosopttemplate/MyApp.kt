package org.sopt.dosopttemplate

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import org.sopt.dosopttemplate.data.local.UserSharedPref
import timber.log.Timber

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initTimber()
        initUserPreference()
        setDayMode()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    private fun initUserPreference() {
        UserSharedPref.initPref(this)
    }

    private fun setDayMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

}