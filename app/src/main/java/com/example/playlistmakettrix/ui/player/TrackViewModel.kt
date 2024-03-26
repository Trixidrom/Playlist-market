package com.example.playlistmakettrix.ui.player

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmakettrix.creator.Creator

class TrackViewModel(
    private val trackId: Long,
    application: Application
) : AndroidViewModel(application) {

    val interactor = Creator.provideSearchInteractor(getApplication())


    private var loadingLiveData = MutableLiveData(true)
    fun getLoadingLiveData(): LiveData<Boolean> = loadingLiveData

    companion object {
        fun getViewModelFactory(trackId: Long): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                TrackViewModel (trackId, this[APPLICATION_KEY] as Application)
            }
        }
    }

}