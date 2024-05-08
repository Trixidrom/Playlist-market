package com.example.playlistmakettrix.di

import com.example.playlistmakettrix.domain.search.SearchInteractor
import com.example.playlistmakettrix.domain.search.impl.SearchInteractorImpl
import com.example.playlistmakettrix.domain.searchhistory.SearchHistoryInteractor
import com.example.playlistmakettrix.domain.searchhistory.impl.SearchHistoryInteractorImpl
import com.example.playlistmakettrix.domain.settings.ThemeSwitchInteractor
import com.example.playlistmakettrix.domain.settings.impl.ThemeSwitchInteractorImpl
import com.example.playlistmakettrix.domain.sharing.SharingInteractor
import com.example.playlistmakettrix.domain.sharing.impl.SharingInteractorImpl
import org.koin.dsl.module

val interactorModule = module {
    factory <SearchInteractor> {
        SearchInteractorImpl(repository = get())
    }

    factory <SearchHistoryInteractor> {
        SearchHistoryInteractorImpl( repository = get())
    }

    factory <SharingInteractor> {
        SharingInteractorImpl(externalNavigator = get())
    }

    factory <ThemeSwitchInteractor>{
        ThemeSwitchInteractorImpl(
            themeSwitchRepository = get()
        )
    }
}