package com.example.playlistmakettrix

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmakettrix.di.dataModule
import com.example.playlistmakettrix.di.interactorModule
import com.example.playlistmakettrix.di.repositoryModule
import com.example.playlistmakettrix.di.viewModelModule
import com.example.playlistmakettrix.domain.settings.ThemeSwitchInteractor
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(repositoryModule, interactorModule, dataModule, viewModelModule)
        }

        val themeSwitcherInteractor: ThemeSwitchInteractor by inject()
        themeSwitcherInteractor.applyCurrentTheme()

    }


}