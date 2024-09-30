package com.example.playlistmakettrix.ui.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playlistmakettrix.R
import com.example.playlistmakettrix.databinding.FragmentFavoritesBinding
import com.example.playlistmakettrix.ui.library.adapters.FavoritesTracksListAdapter
import com.example.playlistmakettrix.ui.library.viewmodels.FavoritesViewModel
import com.example.playlistmakettrix.ui.searhscreen.TrackState
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private val viewModel by viewModel<FavoritesViewModel>()
    private val adapter: FavoritesTracksListAdapter by lazy {
        FavoritesTracksListAdapter()
    }

    companion object {
        private const val SUCCESS = 0
        private const val NOTHING_FOUND = 1
        private const val COMMUNICATION_PROBLEM = 2
        private const val PROGRESS = 3
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        binding.trackList.adapter = adapter
        binding.trackList.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.observeState().observe(viewLifecycleOwner) { loadingState ->
            when (loadingState) {
                is TrackState.Loading -> binding.viewFlipper.displayedChild = PROGRESS
                is TrackState.Error -> {
                    binding.viewFlipper.displayedChild = COMMUNICATION_PROBLEM
                    Toast.makeText(requireContext(), loadingState.errorMessage, Toast.LENGTH_LONG).show()
                }
                is TrackState.Content -> {
                    if (loadingState.trackModel.isNotEmpty()) {
                        binding.viewFlipper.displayedChild = SUCCESS
                        adapter.update(loadingState.trackModel)

                    } else {
                        binding.viewFlipper.displayedChild = NOTHING_FOUND
                    }
                }
            }
        }

        binding.emptyFavorites.emptyContentText.text = getString(R.string.media_library_is_empty)
        binding.viewFlipper.displayedChild = NOTHING_FOUND
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavorites()
    }
}