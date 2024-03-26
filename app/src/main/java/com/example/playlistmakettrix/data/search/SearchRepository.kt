package com.example.playlistmakettrix.data.search

import com.example.playlistmakettrix.domain.search.models.Track
import com.example.playlistmakettrix.util.Resource

interface SearchRepository {
    fun searchTracks(expression: String): Resource<List<Track>>
}