package com.example.playlistmakettrix.domain.settings

interface ThemeSwitchRepository {
    fun switchTheme(darkThemeEnabled: Boolean)
    fun isDarkModeOn(): Boolean
    fun applyCurrentTheme()
}