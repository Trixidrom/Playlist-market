package com.example.playlistmakettrix.di

import com.example.playlistmakettrix.ui.library.viewmodels.FavoritesViewModel
import com.example.playlistmakettrix.ui.library.viewmodels.PlaylistsViewModel
import com.example.playlistmakettrix.ui.player.PlayerViewModel
import com.example.playlistmakettrix.ui.searhscreen.view_model.SearchViewModel
import com.example.playlistmakettrix.ui.settings.view_model.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel<SearchViewModel> {
        SearchViewModel(
            application = get(),
            searchInteractor = get(),
            searchHistoryInteractor = get()
        )
    }

    viewModel<SettingsViewModel> {
        SettingsViewModel(
            sharingInteractor = get(),
            switchThemeInteractor = get()
        )
    }

    viewModel<FavoritesViewModel> {
        FavoritesViewModel()
    }

    viewModel<PlaylistsViewModel> {
        PlaylistsViewModel()
    }

    viewModel {
        PlayerViewModel()
    }
}