package com.example.playlistmakettrix.data.settings.impl

import android.content.SharedPreferences
import com.example.playlistmakettrix.GeneralConstants
import com.example.playlistmakettrix.data.settings.LocalStorage

class ThemeStorage(private val sharedPreferences: SharedPreferences) : LocalStorage {

    override fun switch(darkThemeEnabled: Boolean) {
        sharedPreferences
            .edit()
            .putBoolean(GeneralConstants.MODE_DARK, darkThemeEnabled)
            .apply()
    }

    override fun isDarkModeOn(): Boolean {
        return sharedPreferences.getBoolean(GeneralConstants.MODE_DARK, false)
    }

}