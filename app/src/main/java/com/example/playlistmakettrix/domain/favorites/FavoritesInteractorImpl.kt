package com.example.playlistmakettrix.domain.favorites

import com.example.playlistmakettrix.domain.search.models.Track
import kotlinx.coroutines.flow.Flow

class FavoritesInteractorImpl(private val favoritesRepository: FavoritesRepository) : FavoritesInteractor {
    override fun favoritesTracks(): Flow<List<Track>> {
        return favoritesRepository.favoritesTracks()
    }

    override suspend fun addTrackToFavorites(track: Track) {
        favoritesRepository.addToFavorites(track)
    }

    override suspend fun removeTrackFromFavorites(trackId: Long) {
        favoritesRepository.removeFromFavorites(trackId)
    }

    override suspend fun trackIsExists(trackId: Long): Boolean {
        return favoritesRepository.trackIsExists(trackId)
    }
}