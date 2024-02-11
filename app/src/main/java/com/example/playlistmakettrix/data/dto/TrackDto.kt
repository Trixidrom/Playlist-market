package com.example.playlistmakettrix.data.dto

import com.google.gson.annotations.SerializedName

data class TrackDto (
    @SerializedName("trackId") val trackId: Long,
    @SerializedName("trackName") val trackName: String,
    @SerializedName("collectionName") val collectionName: String?,
    @SerializedName("artistName") val artistName: String,
    @SerializedName("primaryGenreName") val primaryGenreName: String,
    @SerializedName("previewUrl") val previewUrl: String?,
    @SerializedName("country") val country: String,
    @SerializedName("releaseDate") val releaseDate: String,
    @SerializedName("trackTimeMillis") val trackTimeMillis: Int,
    @SerializedName("artworkUrl100") val artworkUrl100: String
)