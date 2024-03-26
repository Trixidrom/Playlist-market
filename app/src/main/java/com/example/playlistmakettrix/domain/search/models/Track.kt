package com.example.playlistmakettrix.domain.search.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Track (
    val trackId: Long,
    val trackName: String,
    val collectionName: String?,
    val artistName: String,
    val primaryGenreName: String,
    val previewUrl: String?,
    val country: String,
    val releaseDate: String,
    val trackTime: String,
    val artworkUrl100: String
): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
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
        p0.writeString(previewUrl)
        p0.writeString(country)
        p0.writeString(releaseDate)
        p0.writeString(trackTime)
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
