package com.example.playlistmakettrix.di

import com.example.playlistmakettrix.data.db.TrackDbConverter
import com.example.playlistmakettrix.data.favorites.FavoritesRepositoryImpl
import com.example.playlistmakettrix.domain.search.SearchRepository
import com.example.playlistmakettrix.data.search.impl.SearchRepositoryImpl
import com.example.playlistmakettrix.domain.searchhistory.SearchHistoryRepository
import com.example.playlistmakettrix.data.searchhistory.impl.SearchHistoryRepositoryImpl
import com.example.playlistmakettrix.data.settings.impl.ThemeSwitchRepositoryImpl
import com.example.playlistmakettrix.domain.favorites.FavoritesRepository
import com.example.playlistmakettrix.domain.settings.ThemeSwitchRepository
import org.koin.dsl.module

val repositoryModule = module {

    single <SearchRepository> {
        SearchRepositoryImpl(networkClient = get())
    }

    single <SearchHistoryRepository> {
        SearchHistoryRepositoryImpl(
            searchHistorySharedPref = get()
        )
    }

    single <ThemeSwitchRepository> {
        ThemeSwitchRepositoryImpl(
            themeStorage = get()
        )
    }

    factory {TrackDbConverter()}

    single<FavoritesRepository> {
        FavoritesRepositoryImpl(
            appDatabase = get(),
            trackConverter = get()
        )
    }
}