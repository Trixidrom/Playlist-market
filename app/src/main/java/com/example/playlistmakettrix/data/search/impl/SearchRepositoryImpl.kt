package com.example.playlistmakettrix.data.search.impl

import com.example.playlistmakettrix.data.dto.TracksSearchRequest
import com.example.playlistmakettrix.data.dto.TracksSearchResponse
import com.example.playlistmakettrix.data.network.NetworkClient
import com.example.playlistmakettrix.domain.search.SearchRepository
import com.example.playlistmakettrix.domain.search.models.Track
import com.example.playlistmakettrix.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.Locale

class SearchRepositoryImpl(private val networkClient: NetworkClient) : SearchRepository {
    override fun searchTracks(expression: String): Flow<Resource<List<Track>>> {
        return flow {
            val response = networkClient.doRequest(TracksSearchRequest(expression = expression))

            when (response.resultCode) {
                200 -> {
                    emit(Resource.Success(
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
                    ))
                }

                400 -> {
                    emit(Resource.Success(emptyList()))
                }

                -1 -> {
                    emit(Resource.Error("Проверьте подключение к интернету", errorCode = -1))
                }

                else -> {
                    emit(Resource.Error("Ошибка сервера", errorCode = 0))
                }
            }
        }
    }
}