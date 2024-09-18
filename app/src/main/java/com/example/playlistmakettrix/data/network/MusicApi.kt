package com.example.playlistmakettrix.data.network

import com.example.playlistmakettrix.data.dto.TracksSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicApi {

    @GET("/search?entity=song")
    suspend fun searchTracks(@Query("term") text: String): TracksSearchResponse
}