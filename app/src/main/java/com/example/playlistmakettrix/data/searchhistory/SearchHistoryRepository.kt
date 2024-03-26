package com.example.playlistmakettrix.data.searchhistory

import com.example.playlistmakettrix.domain.search.models.Track

interface SearchHistoryRepository {
    fun getHistory(): MutableList<Track>

    fun clearHistory()

    fun saveHistory(history: MutableList<Track>)
}