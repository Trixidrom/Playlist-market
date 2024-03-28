package com.example.playlistmakettrix.ui.player

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TrackViewModel(
    private val trackId: Long,
) : ViewModel() {

    private var loadingLiveData = MutableLiveData(true)
    fun getLoadingLiveData(): LiveData<Boolean> = loadingLiveData

}