package com.example.playlistmakettrix.domain.search

import com.example.playlistmakettrix.domain.search.models.Track
import com.example.playlistmakettrix.util.Resource
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun searchTracks(expression: String): Flow<Resource<List<Track>>>
}