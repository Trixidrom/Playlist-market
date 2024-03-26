package com.example.playlistmakettrix.ui.searhscreen

import com.example.playlistmakettrix.domain.search.models.Track

sealed class TrackState {
    object Loading: TrackState()
    object Error: TrackState()
    data class Content(
        val trackModel: List<Track>,
    ): TrackState()
}