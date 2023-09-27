package com.example.playlistmarkettrix

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.playlistmarkettrix.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSearch.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
        binding.buttonLibrary.setOnClickListener {
            val intent = Intent(this, LibraryActivity::class.java)
            startActivity(intent)
        }
        binding.buttonSettings.setOnClickListener{
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
}