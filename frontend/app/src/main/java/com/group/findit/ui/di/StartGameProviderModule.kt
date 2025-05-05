package com.group.findit.ui.di

import android.content.Context
import android.net.ConnectivityManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Dagger module for providing dependencies.
 * Supplies instances of [ConnectivityManager] and [Retrofit].
 */
@Module
@InstallIn(SingletonComponent::class)
class StartGameProviderModule {

    /**
     * Provides an instance of [ConnectivityManager].
     * @param context The application context.
     * @return A [ConnectivityManager] instance.
     */
    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    /**
     * Provides a singleton instance of [Retrofit].
     * Configured with a base URL and Moshi converter.
     * @return A [Retrofit] instance.
     */
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://randomuser.me/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
}