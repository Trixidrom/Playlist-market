package com.example.playlistmakettrix.data.settings

interface LocalStorage {
    fun switch(darkThemeEnabled: Boolean)
    fun isDarkModeOn(): Boolean
}