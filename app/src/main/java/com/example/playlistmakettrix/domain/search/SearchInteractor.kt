package com.example.playlistmakettrix.domain.search

import com.example.playlistmakettrix.domain.search.models.Track
import kotlinx.coroutines.flow.Flow

interface SearchInteractor {
    fun searchTracks(expression: String): Flow<Triple<List<Track>?, Int?, String?>>
}