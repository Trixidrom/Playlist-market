package com.example.playlistmakettrix

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.playlistmakettrix.databinding.ActivityAudioPlayerScreenBinding
import com.example.playlistmakettrix.search.models.Track
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Locale

class AudioPlayerScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAudioPlayerScreenBinding
    private lateinit var track: Track


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAudioPlayerScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        track = getTrackFromIntent()
        bindTrack()
        binding.arrowButton.setOnClickListener {
            this.finish()
        }
    }

    private fun getTrackFromIntent(): Track{
        val intent = intent
        val json = intent.getStringExtra(Intent.EXTRA_TEXT)
        return Gson().fromJson(json, Track::class.java)
    }

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