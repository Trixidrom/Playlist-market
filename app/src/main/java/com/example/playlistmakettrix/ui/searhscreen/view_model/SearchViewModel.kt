package com.example.playlistmakettrix.ui.searhscreen.view_model

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.playlistmakettrix.data.searchhistory.impl.SearchHistoryRepositoryImpl
import com.example.playlistmakettrix.domain.search.SearchInteractor
import com.example.playlistmakettrix.domain.search.models.Track
import com.example.playlistmakettrix.domain.searchhistory.SearchHistoryInteractor
import com.example.playlistmakettrix.ui.searhscreen.TrackState

class SearchViewModel(
    application: Application,
    private val searchInteractor: SearchInteractor,
    private val searchHistoryInteractor: SearchHistoryInteractor
) : AndroidViewModel(application) {

    var historyList: MutableList<Track>

    private var loadingLiveData = MutableLiveData<TrackState>()
    fun observeState(): LiveData<TrackState> = loadingLiveData

    private var latestSearchText: String? = null
    private var isClickedAllowed = true
    private val handler = Handler(Looper.getMainLooper())

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

            handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
            searchInteractor.searchTracks(
                expression = expression,
                consumer = object : SearchInteractor.TracksConsumer {
                    override fun consume(foundTracks: List<Track>?, errorMessage: String?) {
                        if (foundTracks != null) {
                            loadingLiveData.postValue(TrackState.Content(foundTracks))
                        } else {
                            loadingLiveData.postValue(TrackState.Error)
                        }
                    }
                }
            )
        }
    }


    fun clickDebounce(): Boolean {
        val current = isClickedAllowed
        if (isClickedAllowed) {
            isClickedAllowed = false
            handler.postDelayed({ isClickedAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }

    fun searchDebounce(changedText: String) {
        if (latestSearchText == changedText) return

        this.latestSearchText = changedText

        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
        val searchRunnable = Runnable { search(changedText) }
        val postTime = SystemClock.uptimeMillis() + SEARCH_DEBOUNCE_DELAY
        handler.postAtTime(
            searchRunnable,
            SEARCH_REQUEST_TOKEN,
            postTime,
        )
    }

    //очистка handler от задач
    override fun onCleared() {
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
    }

    companion object {
        private val SEARCH_REQUEST_TOKEN = Any()
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }
}