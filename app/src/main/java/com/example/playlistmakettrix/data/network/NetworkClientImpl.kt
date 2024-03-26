package com.example.playlistmakettrix.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.playlistmakettrix.data.dto.BaseResponse
import com.example.playlistmakettrix.data.dto.TracksSearchRequest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkClientImpl(private val context: Context): NetworkClient {

    companion object {
        private const val BASE_URL = "https://itunes.apple.com"
    }

    private val httpLoggingInterceptor = HttpLoggingInterceptor()

    private val okHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor { chain ->
            chain.proceed(
                chain.request()
                    .newBuilder()
                    .build()
            )
        }.addInterceptor(httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val musicService: MusicApi = retrofit.create(MusicApi::class.java)

    override fun doRequest(dto: Any): BaseResponse {
        if (!isConnected(context)) {
            val response = BaseResponse()
            response.resultCode = -1
            return response
        }

        return if (dto is TracksSearchRequest) {
            val resp = musicService.searchTracks(dto.expression).execute()
            val body = resp.body() ?: BaseResponse()

            body.apply { resultCode = 200 }
        } else {
            BaseResponse().apply { resultCode = 400 }
        }
    }
    private fun isConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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