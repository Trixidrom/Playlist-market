package com.example.playlistmakettrix.retrofit

import com.example.playlistmakettrix.search.models.TracksSearchListModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicApi {

    @GET("/search?entity=song")
    fun searchTracks(
        @Query("term") text: String
    ): Call<TracksSearchListModel>
}