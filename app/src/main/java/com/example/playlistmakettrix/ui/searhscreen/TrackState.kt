package com.example.playlistmakettrix.ui.searhscreen

import com.example.playlistmakettrix.domain.search.models.Track

sealed class TrackState {
    object Loading: TrackState()
    data class Error(
        val errorCode: Int,
        val errorMessage: String = ""
    ): TrackState()
    data class Content(
        val trackModel: List<Track>,
    ): TrackState()
}