package com.example.playlistmakettrix.domain.impl

import com.example.playlistmakettrix.domain.api.TracksInteractor
import com.example.playlistmakettrix.domain.api.TracksRepository
import java.util.concurrent.Executors

class TracksInteractorImpl (private val repository: TracksRepository) : TracksInteractor {

    private val executor = Executors.newCachedThreadPool()

    override fun searchTracks ( expression: String, consumer: TracksInteractor.TracksConsumer) {
        executor.execute {
            consumer.consume(repository.searchTracks(expression))
        }
    }
}