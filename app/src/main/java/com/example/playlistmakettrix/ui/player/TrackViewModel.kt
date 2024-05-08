package com.example.playlistmakettrix.ui.player

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TrackViewModel : ViewModel() {

    private var loadingLiveData = MutableLiveData(true)
    fun getLoadingLiveData(): LiveData<Boolean> = loadingLiveData

}