package com.example.playlistmakettrix.data.searchhistory.impl

import android.app.Application
import android.content.SharedPreferences
import androidx.activity.ComponentActivity
import com.example.playlistmakettrix.GeneralConstants
import com.example.playlistmakettrix.data.searchhistory.SearchHistoryRepository
import com.example.playlistmakettrix.domain.search.models.Track
import com.google.gson.Gson

class SearchHistoryRepositoryImpl (application: Application): SearchHistoryRepository {

    private val searchHistorySharedPref: SharedPreferences =
        application.getSharedPreferences(GeneralConstants.PLAY_LIST_MAKET_SHARED_PREFF, ComponentActivity.MODE_PRIVATE)

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