package com.example.playlistmakettrix.domain.searchhistory

import com.example.playlistmakettrix.domain.search.models.Track

interface SearchHistoryInteractor {
    fun getHistory(): MutableList<Track>

    fun clearHistory()

    fun saveHistory(history: MutableList<Track>)
}