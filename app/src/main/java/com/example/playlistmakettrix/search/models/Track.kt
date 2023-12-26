package com.example.playlistmakettrix.search.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Track (
    @SerializedName("trackId") val trackId: Long,
    @SerializedName("trackName") val trackName: String,
    @SerializedName("collectionName") val collectionName: String?,
    @SerializedName("artistName") val artistName: String,
    @SerializedName("primaryGenreName") val primaryGenreName: String,
    @SerializedName("country") val country: String,
    @SerializedName("releaseDate") val releaseDate: String,
    @SerializedName("trackTimeMillis") val trackTimeMillis: Int,
    @SerializedName("artworkUrl100") val artworkUrl100: String
): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: ""
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel, p1: Int) {
        p0.writeLong(trackId)
        p0.writeString(trackName)
        p0.writeString(collectionName)
        p0.writeString(artistName)
        p0.writeString(primaryGenreName)
        p0.writeString(country)
        p0.writeString(releaseDate)
        p0.writeInt(trackTimeMillis)
        p0.writeString(artworkUrl100)
    }

    companion object CREATOR : Parcelable.Creator<Track> {
        override fun createFromParcel(parcel: Parcel): Track {
            return Track(parcel)
        }

        override fun newArray(size: Int): Array<Track?> {
            return arrayOfNulls(size)
        }
    }
}
