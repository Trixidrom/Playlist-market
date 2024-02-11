package com.example.playlistmakettrix.data

import com.example.playlistmakettrix.data.dto.TracksSearchRequest
import com.example.playlistmakettrix.data.dto.TracksSearchResponse
import com.example.playlistmakettrix.data.network.NetworkClient
import com.example.playlistmakettrix.domain.api.TracksRepository
import com.example.playlistmakettrix.domain.models.Track
import java.text.SimpleDateFormat
import java.util.Locale

class TrackSearchRepositoryImpl(private val networkClient: NetworkClient) : TracksRepository {
    override fun searchTracks(expression: String): List<Track> {
        val response = networkClient.doRequest(TracksSearchRequest(expression = expression))

        if (response.resultCode == 200) {
            return (response as TracksSearchResponse).trackList.map { trackDto ->
                Track(
                    trackId = trackDto.trackId,
                    trackName = trackDto.trackName,
                    collectionName = trackDto.collectionName,
                    artistName = trackDto.artistName,
                    primaryGenreName = trackDto.primaryGenreName,
                    previewUrl = trackDto.previewUrl,
                    country = trackDto.country,
                    releaseDate = trackDto.releaseDate,
                    trackTime = SimpleDateFormat("mm:ss", Locale.getDefault()).format(trackDto.trackTimeMillis),
                    artworkUrl100 = trackDto.artworkUrl100,
                )
            }
        } else {
            return emptyList()
        }
    }
}