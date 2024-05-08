package com.example.playlistmakettrix.domain.settings

interface ThemeSwitchInteractor {
    fun switch(isDarkModeOn: Boolean)
    fun isDarkModeOn(): Boolean
    fun applyCurrentTheme()
}