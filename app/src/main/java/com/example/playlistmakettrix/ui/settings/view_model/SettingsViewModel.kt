package com.example.playlistmakettrix.ui.settings.view_model

import androidx.lifecycle.ViewModel
import com.example.playlistmakettrix.domain.sharing.SharingInteractor

class SettingsViewModel(
    private val sharingInteractor: SharingInteractor
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

}