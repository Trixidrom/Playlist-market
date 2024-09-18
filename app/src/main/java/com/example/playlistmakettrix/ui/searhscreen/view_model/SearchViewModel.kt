package com.example.playlistmakettrix.ui.searhscreen.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.playlistmakettrix.data.searchhistory.impl.SearchHistoryRepositoryImpl
import com.example.playlistmakettrix.domain.search.SearchInteractor
import com.example.playlistmakettrix.domain.search.models.Track
import com.example.playlistmakettrix.domain.searchhistory.SearchHistoryInteractor
import com.example.playlistmakettrix.ui.searhscreen.TrackState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel(
    application: Application,
    private val searchInteractor: SearchInteractor,
    private val searchHistoryInteractor: SearchHistoryInteractor
) : AndroidViewModel(application) {

    var historyList: MutableList<Track>

    private var loadingLiveData = MutableLiveData<TrackState>()
    fun observeState(): LiveData<TrackState> = loadingLiveData

    private var latestSearchText: String? = null

    private var searchJob: Job? = null

    init {
        historyList = getSearchHistory()
    }

    private fun getSearchHistory(): MutableList<Track> {
        return searchHistoryInteractor.getHistory()
    }

    fun clearSearchHistory() {
        searchHistoryInteractor.clearHistory()
    }

    private fun saveHistoryList(historyList: MutableList<Track>) {
        searchHistoryInteractor.saveHistory(historyList)
    }

    fun addTrackToHistoryList(track: Track) {
        if (historyList.contains(track)) {
            historyList.remove(track)
            historyList.add(0, track)
        } else {
            historyList.add(0, track)
        }

        if (historyList.size == SearchHistoryRepositoryImpl.SEARCH_HISTORY_SIZE + 1) {
            historyList.removeAt(SearchHistoryRepositoryImpl.SEARCH_HISTORY_SIZE)
        }

        saveHistoryList(historyList)
    }

    fun search(expression: String) {
        if (expression.isNotEmpty()) {
            loadingLiveData.postValue(TrackState.Loading)

            viewModelScope.launch {
                searchInteractor
                    .searchTracks(expression)
                    .collect { pair ->
                        val tracks = mutableListOf<Track>()
                        if (pair.first != null) {
                            tracks.addAll(pair.first!!)
                        }

                        if (pair.second != null && pair.third != null) {
                            loadingLiveData.postValue(TrackState.Error(errorMessage = pair.third!!, errorCode = pair.second!!))
                        } else {
                            loadingLiveData.postValue(TrackState.Content(tracks))
                        }
                    }
            }
        }
    }

    fun searchDebounce(changedText: String) {
        if (latestSearchText == changedText) return

        latestSearchText = changedText

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY)
            search(changedText)
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L

    }
}