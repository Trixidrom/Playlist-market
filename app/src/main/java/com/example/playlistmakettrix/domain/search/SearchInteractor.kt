package com.example.playlistmakettrix.domain.search

import com.example.playlistmakettrix.domain.search.models.Track

interface SearchInteractor {
    fun searchTracks(expression: String, consumer: TracksConsumer)

    interface TracksConsumer {
        fun consume(foundTracks: List<Track>?, errorMessage: String?)
    }
}