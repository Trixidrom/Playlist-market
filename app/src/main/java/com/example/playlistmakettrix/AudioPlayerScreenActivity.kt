package com.example.playlistmakettrix

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.bumptech.glide.Glide
import com.example.playlistmakettrix.databinding.ActivityAudioPlayerScreenBinding
import com.example.playlistmakettrix.search.models.Track
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Locale

class AudioPlayerScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAudioPlayerScreenBinding
    private lateinit var track: Track
    private var playerState = STATE_DEFAULT
    private var mediaPlayer = MediaPlayer()
    private var mainThreadHandler: Handler? = null
    private lateinit var updateTimeRunnable: Runnable

    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
        private const val UPDATE_TIMER_DELAY = 500L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAudioPlayerScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainThreadHandler = Handler(Looper.getMainLooper())
        updateTimeRunnable = object : Runnable {
            override fun run() {
                if(mediaPlayer.isPlaying){
                    mainThreadHandler?.postDelayed(this, UPDATE_TIMER_DELAY)
                    binding.playBackTime.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(mediaPlayer.currentPosition)
                }
            }
        }

        track = getTrackFromIntent()
        bindTrack()
        preparePlayer(track.previewUrl)
        binding.arrowButton.setOnClickListener {
            this.finish()
        }

        binding.playButton.setOnClickListener {
            when (playerState) {
                STATE_PLAYING -> pausePlayer()
                STATE_PAUSED, STATE_PREPARED -> startPlayer()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainThreadHandler?.removeCallbacks(updateTimeRunnable)
        mediaPlayer.release() //освобождение ресурсов
    }

    private fun preparePlayer(previewUrl: String?) {
        mediaPlayer.setDataSource(previewUrl)

        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            binding.playButton.isClickable = true
            binding.playButton.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.play_button_ready))
            playerState = STATE_PREPARED
        }
        mediaPlayer.setOnCompletionListener {
//            метод отслеживания завершения воспроизведения. После того как аудио закончило воспроизводиться, часто требуется произвести
//            какие-то изменения на экране: например, сбросить таймер или изменить состояние кнопки. Чтобы иметь возможность отловить
//            этот момент, медиаплееру нужно установить
            binding.playButton.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.play_button_ready))
            mainThreadHandler?.removeCallbacks(updateTimeRunnable)
            binding.playBackTime.text = getString(R.string.timer_start_position)
            playerState = STATE_PREPARED
        }
    }

    private fun startPlayer() {
        mediaPlayer.start()
        binding.playButton.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.pause_button))
        playerState = STATE_PLAYING
        mainThreadHandler?.post(updateTimeRunnable)
    }

    private fun pausePlayer() {
        mediaPlayer.pause()
        binding.playButton.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.play_button_ready))
        playerState = STATE_PAUSED
    }

    private fun getTrackFromIntent(): Track{
        val intent = intent
        val json = intent.getStringExtra(Intent.EXTRA_TEXT)
        return Gson().fromJson(json, Track::class.java)
    }

    //заполняем информацию страницы
    private fun bindTrack(){
        binding.title.text = track.trackName
        binding.artist.text = track.artistName
        binding.durationValue.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)

        if (track.collectionName != null) {
            binding.albumGroup.visibility = View.VISIBLE
            binding.albumValue.text = track.collectionName
        } else {
            binding.albumGroup.visibility = View.GONE
        }

        binding.yearValue.text = track.releaseDate.substring(0, 4)
        binding.genreValue.text = track.primaryGenreName
        binding.countryValue.text = track.country

        Glide.with(binding.root)
            .load(track.artworkUrl100.replace("100x100bb.jpg", "512x512bb.jpg", true))
            .placeholder(R.drawable.album)
            .centerCrop()
            .into(binding.cover)
    }

}