package com.example.playlistmakettrix

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class App : Application() {

    var darkTheme = false
    override fun onCreate() {
        super.onCreate()

        val sharPref = getSharedPreferences(GeneralConstants.PLAY_LIST_MAKET, MODE_PRIVATE)
        darkTheme = sharPref.getBoolean(GeneralConstants.MODE_DARK, false)
        switchTheme(darkTheme)
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        darkTheme = darkThemeEnabled
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}