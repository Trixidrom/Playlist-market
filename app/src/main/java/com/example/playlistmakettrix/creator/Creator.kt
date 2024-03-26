package com.example.playlistmakettrix.creator

import android.app.Application
import com.example.playlistmakettrix.data.network.NetworkClientImpl
import com.example.playlistmakettrix.domain.search.SearchInteractor
import com.example.playlistmakettrix.data.search.impl.SearchRepositoryImpl
import com.example.playlistmakettrix.data.searchhistory.SearchHistoryRepository
import com.example.playlistmakettrix.data.searchhistory.impl.SearchHistoryRepositoryImpl
import com.example.playlistmakettrix.data.sharing.ExternalNavigator
import com.example.playlistmakettrix.data.sharing.impl.ExternalNavigatorImpl
import com.example.playlistmakettrix.domain.search.impl.SearchInteractorImpl
import com.example.playlistmakettrix.domain.searchhistory.SearchHistoryInteractor
import com.example.playlistmakettrix.domain.searchhistory.impl.SearchHistoryInteractorImpl
import com.example.playlistmakettrix.domain.sharing.SharingInteractor
import com.example.playlistmakettrix.domain.sharing.impl.SharingInteractorImpl

object Creator {

    private fun searchHistoryRepository(application: Application): SearchHistoryRepository {
        return SearchHistoryRepositoryImpl(application)
    }

    fun provideSearchHistoryInteractor(application: Application): SearchHistoryInteractor {
        return SearchHistoryInteractorImpl(searchHistoryRepository(application))
    }

    private fun getSearchRepository(application: Application): SearchRepositoryImpl {
        return SearchRepositoryImpl(NetworkClientImpl(application))
    }

    fun provideSearchInteractor(application: Application): SearchInteractor {
        return SearchInteractorImpl(getSearchRepository(application))
    }

    private fun getExternalNavigator(application: Application): ExternalNavigator{
        return ExternalNavigatorImpl(application)
    }

    fun provideSharingInteractor(application: Application): SharingInteractor {
        return SharingInteractorImpl(
            getExternalNavigator(application)
        )
    }
}