package com.example.playlistmakettrix.ui.settings.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmakettrix.creator.Creator
import com.example.playlistmakettrix.data.sharing.ExternalNavigator
import com.example.playlistmakettrix.data.sharing.impl.ExternalNavigatorImpl
import com.example.playlistmakettrix.domain.sharing.SharingInteractor
import com.example.playlistmakettrix.domain.settings.SettingsInteractor
import com.example.playlistmakettrix.domain.settings.impl.SettingsInteractorImpl
import com.example.playlistmakettrix.domain.sharing.impl.SharingInteractorImpl

class SettingsViewModel(
    application: Application,
    private val settingsInteractor: SettingsInteractor,
) : AndroidViewModel(application) {

    private val sharingInteractor = Creator.provideSharingInteractor(application)

    fun shareApp (){
        sharingInteractor.shareApp()
    }

    fun openTerms(){
        sharingInteractor.openTerms()
    }

    fun openSupport(){
        sharingInteractor.openSupport()
    }

    companion object {
        private val SEARCH_REQUEST_TOKEN = Any()

        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private const val CLICK_DEBOUNCE_DELAY = 1000L
        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SettingsViewModel(
                    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application,
                    SettingsInteractorImpl(),
                )
            }
        }
    }
}