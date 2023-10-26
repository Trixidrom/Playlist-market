package com.example.playlistmakettrix.search.models

import com.google.gson.annotations.SerializedName

class TracksSearchListModel(
    @SerializedName ("resultCount") val resultCount: Int = 0,
    @SerializedName ("results") val trackList: List<Track> = emptyList()
)
