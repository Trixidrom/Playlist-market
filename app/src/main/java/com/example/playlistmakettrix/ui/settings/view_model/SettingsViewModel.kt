package com.example.playlistmakettrix.ui.settings.view_model

import androidx.lifecycle.ViewModel
import com.example.playlistmakettrix.domain.settings.ThemeSwitchInteractor
import com.example.playlistmakettrix.domain.sharing.SharingInteractor

class SettingsViewModel(
    private val sharingInteractor: SharingInteractor,
    private val switchThemeInteractor: ThemeSwitchInteractor
) : ViewModel(){

    fun shareApp (){
        sharingInteractor.shareApp()
    }

    fun openTerms(){
        sharingInteractor.openTerms()
    }

    fun openSupport(){
        sharingInteractor.openSupport()
    }

    fun switchTheme(isChecked: Boolean) {
        switchThemeInteractor.switch(isChecked)
    }

    fun isDarkThemeOn(): Boolean {
        return switchThemeInteractor.isDarkModeOn()
    }

}