package com.example.playlistmakettrix.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TrackDao {
    @Insert (entity = TrackEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track: TrackEntity)

    @Query ("SELECT * FROM favorites_track_entity ORDER BY createdAt DESC")
    suspend fun getTracks(): List<TrackEntity>

    @Query("SELECT EXISTS(SELECT * FROM favorites_track_entity WHERE id = :id)")
    suspend fun trackIsExist(id: Long): Boolean

    @Query ("DELETE FROM favorites_track_entity WHERE id = :id")
    suspend fun deleteTrack(id: Long)
}