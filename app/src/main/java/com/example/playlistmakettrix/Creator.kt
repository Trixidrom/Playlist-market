package com.example.playlistmakettrix

import com.example.playlistmakettrix.data.TrackSearchRepositoryImpl
import com.example.playlistmakettrix.data.network.ApiService
import com.example.playlistmakettrix.domain.api.TracksInteractor
import com.example.playlistmakettrix.domain.api.TracksRepository
import com.example.playlistmakettrix.domain.impl.TracksInteractorImpl

object Creator {
    private fun getTracksRepository(): TracksRepository {
        return TrackSearchRepositoryImpl(ApiService)
    }

    fun provideTracksInteractor(): TracksInteractor {
        return TracksInteractorImpl(getTracksRepository())
    }
}