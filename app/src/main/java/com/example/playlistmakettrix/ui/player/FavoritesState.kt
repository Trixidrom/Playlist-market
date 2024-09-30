package com.example.playlistmakettrix.ui.player

sealed class FavoritesState {
    class Progress : FavoritesState()
    class Success(val isExists: Boolean) : FavoritesState()
}