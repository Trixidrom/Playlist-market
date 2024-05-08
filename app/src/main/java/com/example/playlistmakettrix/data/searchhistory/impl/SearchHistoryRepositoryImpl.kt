package com.example.playlistmakettrix.data.searchhistory.impl

import android.content.SharedPreferences
import com.example.playlistmakettrix.domain.searchhistory.SearchHistoryRepository
import com.example.playlistmakettrix.domain.search.models.Track
import com.google.gson.Gson

class SearchHistoryRepositoryImpl (
    private val searchHistorySharedPref: SharedPreferences
): SearchHistoryRepository {
    companion object {
        const val HISTORY_SHAR_PREF_KEY = "history_shared_preferences_key"
        const val SEARCH_HISTORY_SIZE = 10
    }

    override fun getHistory(): MutableList<Track> {
        val json = searchHistorySharedPref.getString(HISTORY_SHAR_PREF_KEY, null) ?: return arrayListOf()
        return Gson().fromJson(json, Array<Track>::class.java).toMutableList()
    }

    override fun clearHistory(){
        searchHistorySharedPref.edit()
            .clear()
            .apply()
    }

    override fun saveHistory(historyList: MutableList<Track>){
        searchHistorySharedPref.edit()
            .putString(HISTORY_SHAR_PREF_KEY, Gson().toJson(historyList))
            .apply()
    }

}