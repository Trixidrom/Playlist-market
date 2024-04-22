package com.example.playlistmakettrix.ui.library

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.playlistmakettrix.R
import com.example.playlistmakettrix.databinding.ActivityLibraryBinding
import com.example.playlistmakettrix.ui.library.adapters.LibraryViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class LibraryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLibraryBinding
    private lateinit var tabLayoutMediator: TabLayoutMediator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLibraryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tabList = listOf(getString(R.string.favorites_tracks), getString(R.string.playlists))

        binding.viewPager.adapter = LibraryViewPagerAdapter(supportFragmentManager, lifecycle, tabList)

        tabLayoutMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabList[position]
        }
        tabLayoutMediator.attach()

        binding.topAppBar.setNavigationOnClickListener { finish() }
    }

    override fun onDestroy() {
        super.onDestroy()
        tabLayoutMediator.detach()
    }
}