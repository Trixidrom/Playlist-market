package com.example.playlistmakettrix.data.favorites

import com.example.playlistmakettrix.data.db.AppDatabase
import com.example.playlistmakettrix.data.db.TrackDbConverter
import com.example.playlistmakettrix.domain.favorites.FavoritesRepository
import com.example.playlistmakettrix.domain.search.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FavoritesRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val trackConverter: TrackDbConverter
) : FavoritesRepository {
    override fun favoritesTracks(): Flow<List<Track>> {
        return flow{
            val tracks = appDatabase.trackDao().getTracks().map { trackConverter.map(it) }
            emit(tracks)
        }
    }

    override suspend fun addToFavorites(track: Track) {
        val trackEntity = trackConverter.map(track)
        appDatabase.trackDao().insertTrack(trackEntity)
    }

    override suspend fun removeFromFavorites(trackId: Long) {
        appDatabase.trackDao().deleteTrack(trackId)
    }

    override suspend fun trackIsExists(trackId: Long): Boolean {
        return appDatabase.trackDao().trackIsExist(trackId)
    }
}