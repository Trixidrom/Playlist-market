package com.example.playlistmakettrix.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database (version = 1, entities = [TrackEntity::class])
@TypeConverters (DBConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun trackDao(): TrackDao
}