package com.example.playlistmakettrix.data.network

import com.example.playlistmakettrix.data.dto.BaseResponse

interface NetworkClient {
    fun doRequest(dto: Any): BaseResponse
}