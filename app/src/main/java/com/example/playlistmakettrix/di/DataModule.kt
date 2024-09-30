package com.example.playlistmakettrix.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.playlistmakettrix.GeneralConstants
import com.example.playlistmakettrix.data.db.AppDatabase
import com.example.playlistmakettrix.data.network.NetworkClient
import com.example.playlistmakettrix.data.network.NetworkClientImpl
import com.example.playlistmakettrix.data.settings.LocalStorage
import com.example.playlistmakettrix.data.settings.impl.ThemeStorage
import com.example.playlistmakettrix.domain.sharing.ExternalNavigator
import com.example.playlistmakettrix.data.sharing.impl.ExternalNavigatorImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
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

    //room
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db")
            .build()
    }

    //externalNavigator
    single <ExternalNavigator> {
        ExternalNavigatorImpl(context = get())
    }

    single<LocalStorage>{
        ThemeStorage(sharedPreferences = get())
    }

    single<SharedPreferences> {
        androidContext().getSharedPreferences(GeneralConstants.PLAY_LIST_MAKET_SHARED_PREFF, Context.MODE_PRIVATE)
    }
}