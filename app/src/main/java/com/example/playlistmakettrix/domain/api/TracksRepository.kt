package com.example.playlistmakettrix.domain.api

import com.example.playlistmakettrix.domain.models.Track

interface TracksRepository {
    fun searchTracks(expression: String): List<Track>
}