package com.example.playlistmakettrix.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.Date

@Entity ("favorites_track_entity")
data class TrackEntity (
    @PrimaryKey
    val id: Long,
    val imageUrl100: String,
    val trackName: String,
    val artistName: String,
    val collectionName: String?,
    val releaseDate: String,
    val primaryGenreName: String,
    val country: String,
    val trackTime: String,
    val previewUrl: String?,
    val createdAt: Date
)