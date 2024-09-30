package com.example.playlistmakettrix.data.db

import androidx.room.TypeConverter
import java.util.Date

class DBConverter {
    @TypeConverter
    fun dateToLong(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun longToDate(dateLong: Long?): Date? {
        return if (dateLong != null) Date(dateLong) else null
    }
}