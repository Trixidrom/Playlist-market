package com.example.playlistmakettrix.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.playlistmakettrix.data.dto.BaseResponse
import com.example.playlistmakettrix.data.dto.TracksSearchRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class NetworkClientImpl(private val context: Context, retrofit: Retrofit) : NetworkClient {

    companion object {
        const val BASE_URL = "https://itunes.apple.com"
    }

    private val musicService: MusicApi = retrofit.create(MusicApi::class.java)

    override suspend fun doRequest(dto: Any): BaseResponse {
        if (!isConnected(context)) {
            return BaseResponse().apply { resultCode = -1 }
        }

        if (dto !is TracksSearchRequest) {
            return BaseResponse().apply { resultCode = 400 }
        }

        return withContext(Dispatchers.IO) {
            try {
                val response = musicService.searchTracks(dto.expression)
                response.apply { resultCode = 200 }
            } catch (e: Exception) {
                BaseResponse().apply {
                    resultCode = 500
                }
            }
        }
    }

    private fun isConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }
}