package com.example.playlistmakettrix

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmakettrix.di.dataModule
import com.example.playlistmakettrix.di.interactorModule
import com.example.playlistmakettrix.di.repositoryModule
import com.example.playlistmakettrix.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    var darkTheme = false
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(repositoryModule, interactorModule, dataModule, viewModelModule)
        }

        val sharPref = getSharedPreferences(GeneralConstants.PLAY_LIST_MAKET_SHARED_PREFF, MODE_PRIVATE)
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