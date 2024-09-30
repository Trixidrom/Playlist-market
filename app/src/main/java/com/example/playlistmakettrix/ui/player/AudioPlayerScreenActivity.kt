package com.example.playlistmakettrix.ui.player

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.bumptech.glide.Glide
import com.example.playlistmakettrix.R
import com.example.playlistmakettrix.databinding.ActivityAudioPlayerScreenBinding
import com.example.playlistmakettrix.domain.search.models.Track
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel

class AudioPlayerScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAudioPlayerScreenBinding
    private lateinit var track: Track
    private val playerViewModel by viewModel<PlayerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAudioPlayerScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        track = getTrackFromIntent()
        playerViewModel.initMediaPlayer(track.previewUrl)

        bindTrack()
        binding.arrowButton.setOnClickListener {
            this.finish()
        }

        binding.playButton.setOnClickListener {
            playerViewModel.onPlayButtonClicked()
        }

        playerViewModel.favoritesIsExists(track.trackId)

        playerViewModel.observePlayerState().observe(this) { playerState ->
            binding.playButton.isEnabled = playerState.isPlayButtonEnabled
            binding.timerTextView.text = playerState.progress
            when (playerState) {
                is PlayerState.Default -> {
                    binding.playButton.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.play_button_not_ready))
                }
                is PlayerState.Paused -> {
                    binding.playButton.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.play_button_ready))
                }
                is PlayerState.Playing -> {
                    binding.playButton.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.pause_button))
                }
                is PlayerState.Prepared -> {
                    binding.playButton.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.play_button_ready))
                }
            }
        }

        playerViewModel.observeFavoritesState().observe(this) { favoritesState ->
            when (favoritesState) {
                is FavoritesState.Progress -> {
                    binding.addToFavoriteButton.isClickable = false
                }

                is FavoritesState.Success -> {
                    binding.addToFavoriteButton.isClickable = true
                    binding.addToFavoriteButton.setImageDrawable(
                        AppCompatResources.getDrawable(this,
                            if (favoritesState.isExists) {
                                R.drawable.ic_favorite_on
                            } else {
                                R.drawable.ic_favorite_off
                            }
                        )
                    )
                }
            }
        }

        binding.addToFavoriteButton.setOnClickListener {
            playerViewModel.clickToFavoritesButton(track)
        }
    }

    override fun onPause() {
        super.onPause()
        playerViewModel.onPause()
    }

    private fun getTrackFromIntent(): Track {
        val intent = intent
        val json = intent.getStringExtra(Intent.EXTRA_TEXT)
        return Gson().fromJson(json, Track::class.java)
    }

    //заполняем информацию страницы
    private fun bindTrack() {
        binding.title.text = track.trackName
        binding.artist.text = track.artistName
        binding.durationValue.text = track.trackTime

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