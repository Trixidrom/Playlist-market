package com.example.playlistmakettrix.domain.search.impl

import com.example.playlistmakettrix.domain.search.SearchInteractor
import com.example.playlistmakettrix.data.search.SearchRepository
import com.example.playlistmakettrix.util.Resource
import java.util.concurrent.Executors

class SearchInteractorImpl (private val repository: SearchRepository) : SearchInteractor {

    private val executor = Executors.newCachedThreadPool()

    override fun searchTracks ( expression: String, consumer: SearchInteractor.TracksConsumer) {
        executor.execute {
            when (val resource = repository.searchTracks(expression)){
                is Resource.Success -> { consumer.consume(resource.data, null)}
                is Resource.Error -> {consumer.consume(null, resource.message)}
            }
        }
    }
}