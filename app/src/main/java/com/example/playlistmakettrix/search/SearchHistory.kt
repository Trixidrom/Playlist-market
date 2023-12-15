package com.example.playlistmakettrix.search

import android.content.SharedPreferences
import com.example.playlistmakettrix.GeneralConstants
import com.example.playlistmakettrix.search.models.Track
import com.google.gson.Gson

class SearchHistory (private val searchHistorySharedPref: SharedPreferences) {

    fun getHistory(): Array<Track> {
        val json = searchHistorySharedPref.getString(GeneralConstants.HISTORY_SHAR_PREF_KEY, null) ?: return emptyArray()
        return Gson().fromJson(json, Array<Track>::class.java)
    }

    fun clearHistory(){
        searchHistorySharedPref.edit()
            .clear()
            .apply()
    }

    fun saveHistory(historyList: MutableList<Track>){
        searchHistorySharedPref.edit()
            .putString(GeneralConstants.HISTORY_SHAR_PREF_KEY, Gson().toJson(historyList))
            .apply()
    }

}