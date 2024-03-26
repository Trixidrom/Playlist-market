package com.example.playlistmakettrix.ui.searhscreen

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmakettrix.creator.Creator
import com.example.playlistmakettrix.domain.search.SearchInteractor
import com.example.playlistmakettrix.domain.search.models.Track

class SearchViewModel(
    application: Application
) : AndroidViewModel(application) {

    val tracksInteractor = Creator.provideSearchInteractor(getApplication())


    private var loadingLiveData = MutableLiveData<TrackState>()
    fun observeState(): LiveData<TrackState> = loadingLiveData

    private var latestSearchText: String? = null
    private var isClickedAllowed = true
    private val handler = Handler(Looper.getMainLooper())
    fun search(expression: String) {
        if (expression.isNotEmpty()) {
            renderState(TrackState.Loading)

            handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
            tracksInteractor.searchTracks(
                expression = expression,
                consumer = object : SearchInteractor.TracksConsumer {
                    override fun consume(foundTracks: List<Track>?, errorMessage: String?) {
                        if (foundTracks != null) {
                            renderState(TrackState.Content(foundTracks))
                        } else {
                            renderState(TrackState.Error)
                        }
                    }
                }
            )
        }
    }

     fun renderState(state: TrackState) {
        loadingLiveData.postValue(state)
    }
    fun clickDebounce(): Boolean {
        val current = isClickedAllowed
        if(isClickedAllowed){
            isClickedAllowed = false
            handler.postDelayed({isClickedAllowed = true}, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }
    fun searchDebounce(changedText: String){
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

    override fun onCleared() {
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
    }

    companion object {
        private val SEARCH_REQUEST_TOKEN = Any()

        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private const val CLICK_DEBOUNCE_DELAY = 1000L
        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SearchViewModel(this[APPLICATION_KEY] as Application)
            }
        }
    }
}