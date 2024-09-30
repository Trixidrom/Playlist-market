package com.example.playlistmakettrix.ui.player

import android.media.MediaPlayer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmakettrix.domain.favorites.FavoritesInteractor
import com.example.playlistmakettrix.domain.search.models.Track
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class PlayerViewModel(
    private val favoritesInteractor: FavoritesInteractor
) : ViewModel() {
    private var mediaPlayer: MediaPlayer = MediaPlayer()
    private var timerJob: Job? = null

    private val playerState = MutableLiveData<PlayerState>(PlayerState.Default())
    fun observePlayerState(): LiveData<PlayerState> = playerState

    private val favoritesState = MutableLiveData<FavoritesState>(FavoritesState.Progress())
    fun observeFavoritesState(): LiveData<FavoritesState> = favoritesState

    companion object {
        private const val UPDATE_TIMER_DELAY = 300L
    }

    override fun onCleared() {
        super.onCleared()
        releasePlayer()
    }

    fun onPause() {
        pausePlayer()
    }

    fun onPlayButtonClicked() {
        when (playerState.value) {
            is PlayerState.Playing -> {
                pausePlayer()
            }

            is PlayerState.Prepared, is PlayerState.Paused -> {
                startPlayer()
            }

            else -> {}
        }
    }

    //подготовить плеер
    fun initMediaPlayer(previewUrl: String?) {
        mediaPlayer.setDataSource(previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            playerState.postValue(PlayerState.Prepared())
        }
        mediaPlayer.setOnCompletionListener {
            //            Метод отслеживания завершения воспроизведения. После того как аудио закончило воспроизводиться, часто требуется произвести
            //            какие-то изменения на экране: например, сбросить таймер или изменить состояние кнопки. Чтобы иметь возможность отловить
            //            этот момент, медиаплееру нужно установить
            playerState.postValue(PlayerState.Prepared())
            timerJob?.cancel()
        }
    }

    private fun startPlayer() {
        mediaPlayer.start()
        playerState.postValue(PlayerState.Playing(getCurrentPlayerPosition()))
        startTimer()
    }

    private fun pausePlayer() {
        mediaPlayer.pause()
        timerJob?.cancel()
        playerState.postValue(PlayerState.Paused(getCurrentPlayerPosition()))
    }

    private fun releasePlayer() {
        mediaPlayer.stop()
        mediaPlayer.release()//освобождение ресурсов
        playerState.value = PlayerState.Default()
    }

    private fun startTimer() {
        timerJob = viewModelScope.launch {
            while (mediaPlayer.isPlaying) {
                delay(UPDATE_TIMER_DELAY)
                playerState.postValue(PlayerState.Playing(getCurrentPlayerPosition()))
            }
        }
    }

    private fun getCurrentPlayerPosition(): String {
        return SimpleDateFormat("mm:ss", Locale.getDefault()).format(mediaPlayer.currentPosition) ?: "00:00"
    }

    fun favoritesIsExists(trackId: Long) {
        viewModelScope.launch {
            favoritesState.postValue(FavoritesState.Progress())
            favoritesState.postValue(FavoritesState.Success(favoritesInteractor.trackIsExists(trackId)))
        }
    }

    fun clickToFavoritesButton(track: Track) {
        when (favoritesState.value){
            is FavoritesState.Progress -> return
            is FavoritesState.Success -> {
                if ((favoritesState.value as FavoritesState.Success).isExists) {
                    removeFromFavorites(track.trackId)
                } else {
                    addToFavorites(track)
                }
            }
            null -> return
        }
    }

    private fun removeFromFavorites(trackId: Long) {
        viewModelScope.launch {
            favoritesState.postValue(FavoritesState.Progress())
            favoritesInteractor.removeTrackFromFavorites(trackId)
            favoritesState.postValue(FavoritesState.Success(false))
        }
    }
    private fun addToFavorites(track: Track) {
        viewModelScope.launch {
            favoritesState.postValue(FavoritesState.Progress())
            favoritesInteractor.addTrackToFavorites(track)
            favoritesState.postValue(FavoritesState.Success(true))
        }
    }
}