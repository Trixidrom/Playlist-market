package com.example.playlistmakettrix.domain.search.impl

import com.example.playlistmakettrix.domain.search.SearchInteractor
import com.example.playlistmakettrix.domain.search.SearchRepository
import com.example.playlistmakettrix.domain.search.models.Track
import com.example.playlistmakettrix.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchInteractorImpl (private val repository: SearchRepository) : SearchInteractor {


    override fun searchTracks ( expression: String): Flow<Triple<List<Track>?, Int?, String?>> {
        return repository.searchTracks(expression).map { result ->
            when (result){
                is Resource.Success -> { Triple(result.data, null, null)}
                is Resource.Error -> {Triple(null, result. errorCode, result.message)}
            }
        }
    }
}