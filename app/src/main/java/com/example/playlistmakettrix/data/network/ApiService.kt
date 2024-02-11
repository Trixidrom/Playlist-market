package com.example.playlistmakettrix.data.network

import com.example.playlistmakettrix.data.dto.BaseResponse
import com.example.playlistmakettrix.data.dto.TracksSearchRequest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService : NetworkClient {
    private const val BASE_URL = "https://itunes.apple.com"

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
        if (dto is TracksSearchRequest) {
            val resp = musicService.searchTracks(dto.expression).execute()
            val body = resp.body() ?: BaseResponse()

            return body.apply { resultCode = 200 }
        } else {
            return BaseResponse().apply { resultCode = 400 }
        }
    }
}