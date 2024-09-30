package com.example.playlistmakettrix.ui.library.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.playlistmakettrix.ui.library.FavoritesFragment
import com.example.playlistmakettrix.ui.library.PlaylistsFragment

class LibraryViewPagerAdapter(fragmentManager: FragmentManager, fragmentLifecycle: Lifecycle, private val tabList: List<String>) : FragmentStateAdapter(fragmentManager, fragmentLifecycle) {
    override fun getItemCount(): Int {
        return tabList.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FavoritesFragment()
            1 -> PlaylistsFragment.newInstance(123)
            else -> throw Exception("Unknown tab")
        }
    }
}