package com.example.playlistmakettrix.data.db

import com.example.playlistmakettrix.domain.search.models.Track
import java.util.Date

class TrackDbConverter {
    fun map (track: Track): TrackEntity {
        return TrackEntity(
            id = track.trackId,
            trackName = track.trackName,
            artistName = track.artistName,
            collectionName = track.collectionName ?: "",
            imageUrl100 = track.artworkUrl100,
            country = track.country,
            releaseDate = track.releaseDate,
            primaryGenreName = track.primaryGenreName,
            trackTime = track.trackTime,
            previewUrl = track.previewUrl,
            createdAt = Date()
        )
    }

    fun map (entity: TrackEntity): Track {
        return Track (
            trackId = entity.id,
            trackName = entity.trackName,
            artistName = entity.artistName,
            collectionName = entity.collectionName,
            artworkUrl100 = entity.imageUrl100,
            country = entity.country,
            releaseDate = entity.releaseDate,
            primaryGenreName = entity.primaryGenreName,
            trackTime = entity.trackTime,
            previewUrl = entity.previewUrl
        )
    }
}