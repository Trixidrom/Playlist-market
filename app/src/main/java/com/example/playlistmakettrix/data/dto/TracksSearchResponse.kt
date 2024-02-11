package com.example.playlistmakettrix.data.dto

import com.google.gson.annotations.SerializedName

class TracksSearchResponse(
    @SerializedName("resultCount") val resultCount: Int = 0,
    @SerializedName("results") val trackList: List<TrackDto> = emptyList()
) : BaseResponse()
