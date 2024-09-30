package com.example.playlistmakettrix.domain.favorites

import com.example.playlistmakettrix.domain.search.models.Track
import kotlinx.coroutines.flow.Flow

interface FavoritesInteractor {
    fun favoritesTracks(): Flow<List<Track>>

    suspend fun addTrackToFavorites(track: Track)

    suspend fun removeTrackFromFavorites(trackId: Long)

    suspend fun trackIsExists(trackId: Long): Boolean
}