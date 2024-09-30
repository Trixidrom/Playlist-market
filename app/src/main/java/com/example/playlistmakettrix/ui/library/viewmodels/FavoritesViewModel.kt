package com.example.playlistmakettrix.ui.library.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmakettrix.domain.favorites.FavoritesInteractor
import com.example.playlistmakettrix.ui.searhscreen.TrackState
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val favoritesInteractor: FavoritesInteractor
) : ViewModel() {

    private var loadingLiveData = MutableLiveData<TrackState>()
    fun observeState(): LiveData<TrackState> = loadingLiveData

    fun getFavorites() {
        loadingLiveData.postValue(TrackState.Loading)

        viewModelScope.launch {
            favoritesInteractor.favoritesTracks().collect{list ->
                loadingLiveData.postValue(TrackState.Content(list))
            }
        }
    }
}