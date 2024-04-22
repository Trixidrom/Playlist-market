package com.example.playlistmakettrix.ui.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.playlistmakettrix.R
import com.example.playlistmakettrix.databinding.FragmentFavoritesBinding
import com.example.playlistmakettrix.ui.library.viewmodels.FavoritesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private val viewModel by viewModel<FavoritesViewModel>()

    companion object {
        private const val SUCCESS = 0
        private const val NOTHING_FOUND = 1
        private const val COMMUNICATION_PROBLEM = 2
        private const val PROGRESS = 3
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.emptyFavorites.emptyContentText.text = getString(R.string.media_library_is_empty)
        binding.viewFlipper.displayedChild = NOTHING_FOUND
    }
}