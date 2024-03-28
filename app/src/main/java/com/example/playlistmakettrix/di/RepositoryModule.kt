package com.example.playlistmakettrix.di

import com.example.playlistmakettrix.data.search.SearchRepository
import com.example.playlistmakettrix.data.search.impl.SearchRepositoryImpl
import com.example.playlistmakettrix.data.searchhistory.SearchHistoryRepository
import com.example.playlistmakettrix.data.searchhistory.impl.SearchHistoryRepositoryImpl
import com.example.playlistmakettrix.data.settings.SettingsRepository
import com.example.playlistmakettrix.data.settings.impl.SettingsRepositoryImpl
import com.example.playlistmakettrix.domain.search.impl.SearchInteractorImpl
import org.koin.dsl.module

val repositoryModule = module {

    single <SearchRepository> {
        SearchRepositoryImpl(networkClient = get())
    }

    single <SearchHistoryRepository> {
        SearchHistoryRepositoryImpl(application = get())
    }

//    single <SettingsRepository> {
//        SettingsRepositoryImpl()
//    }
}