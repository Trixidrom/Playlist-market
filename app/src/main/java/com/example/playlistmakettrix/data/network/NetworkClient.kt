package com.example.playlistmakettrix.data.network

import com.example.playlistmakettrix.data.dto.BaseResponse

interface NetworkClient {
    suspend fun doRequest(dto: Any): BaseResponse
}