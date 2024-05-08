package com.example.playlistmakettrix.domain.searchhistory

import com.example.playlistmakettrix.domain.search.models.Track

interface SearchHistoryRepository {
    fun getHistory(): MutableList<Track>

    fun clearHistory()

    fun saveHistory(history: MutableList<Track>)
}