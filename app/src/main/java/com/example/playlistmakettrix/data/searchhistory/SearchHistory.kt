package com.example.playlistmakettrix.data.searchhistory

import android.content.SharedPreferences
import com.example.playlistmakettrix.domain.models.Track
import com.google.gson.Gson

class SearchHistory (private val searchHistorySharedPref: SharedPreferences) {

    companion object {
        const val HISTORY_SHAR_PREF_KEY = "history_shared_preferences_key"
    }
    fun getHistory(): Array<Track> {
        val json = searchHistorySharedPref.getString(HISTORY_SHAR_PREF_KEY, null) ?: return emptyArray()
        return Gson().fromJson(json, Array<Track>::class.java)
    }

    fun clearHistory(){
        searchHistorySharedPref.edit()
            .clear()
            .apply()
    }

    fun saveHistory(historyList: MutableList<Track>){
        searchHistorySharedPref.edit()
            .putString(HISTORY_SHAR_PREF_KEY, Gson().toJson(historyList))
            .apply()
    }

}