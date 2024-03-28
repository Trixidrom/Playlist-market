package com.example.playlistmakettrix.di

import com.example.playlistmakettrix.data.network.MusicApi
import com.example.playlistmakettrix.data.network.NetworkClient
import com.example.playlistmakettrix.data.network.NetworkClientImpl
import com.example.playlistmakettrix.data.sharing.ExternalNavigator
import com.example.playlistmakettrix.data.sharing.impl.ExternalNavigatorImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    OkHttpClient
    single <OkHttpClient> {

        val httpLoggingInterceptor = HttpLoggingInterceptor()

        OkHttpClient.Builder()
            .addNetworkInterceptor { chain ->
                chain.proceed(
                    chain.request()
                        .newBuilder()
                        .build()
                )
            }
            .addInterceptor(httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    //retrofit
    single <NetworkClient> {
        val retrofit = Retrofit.Builder()
        .baseUrl(NetworkClientImpl.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(get())
        .build()

        NetworkClientImpl (context = get(), retrofit = retrofit)
    }

    //externalNavigator
    single <ExternalNavigator> {
        ExternalNavigatorImpl(context = get())
    }
}