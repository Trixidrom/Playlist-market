package com.example.playlistmakettrix.domain.favorites

import com.example.playlistmakettrix.domain.search.models.Track
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun favoritesTracks(): Flow<List<Track>>

    suspend fun addToFavorites(track: Track)

    suspend fun removeFromFavorites(trackId: Long)

    suspend fun trackIsExists(trackId: Long): Boolean
}