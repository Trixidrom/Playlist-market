package com.example.playlistmakettrix.data.search.impl

import com.example.playlistmakettrix.data.dto.TracksSearchRequest
import com.example.playlistmakettrix.data.dto.TracksSearchResponse
import com.example.playlistmakettrix.data.network.NetworkClient
import com.example.playlistmakettrix.data.search.SearchRepository
import com.example.playlistmakettrix.domain.search.models.Track
import com.example.playlistmakettrix.util.Resource
import java.text.SimpleDateFormat
import java.util.Locale

class SearchRepositoryImpl(private val networkClient: NetworkClient) : SearchRepository {
    override fun searchTracks(expression: String): Resource<List<Track>> {
        val response = networkClient.doRequest(TracksSearchRequest(expression = expression))

        return when (response.resultCode) {
            200 -> {
                Resource.Success(
                    (response as TracksSearchResponse).trackList.map { trackDto ->
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
                )
            }
            400 -> {
                Resource.Success(emptyList())
            }
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }
            else -> {
                Resource.Error("Ошибка сервера")
            }
        }
    }
}