package com.example.playlistmakettrix.domain.searchhistory.impl

import com.example.playlistmakettrix.data.searchhistory.SearchHistoryRepository
import com.example.playlistmakettrix.domain.search.models.Track
import com.example.playlistmakettrix.domain.searchhistory.SearchHistoryInteractor

class SearchHistoryInteractorImpl(private val repository: SearchHistoryRepository) : SearchHistoryInteractor {
    override fun getHistory(): MutableList<Track> {
        return repository.getHistory()
    }

    override fun clearHistory() {
        repository.clearHistory()
    }

    override fun saveHistory(history: MutableList<Track>) {
        repository.saveHistory(history)
    }
}