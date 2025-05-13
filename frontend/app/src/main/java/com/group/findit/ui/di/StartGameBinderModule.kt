package com.group.findit.ui.di

import com.group.findit.ui.data.startgame.StartGameDataSource
import com.group.findit.ui.data.startgame.StartGameDataSourceImpl
import com.group.findit.ui.startgame.StartGameRepository
import com.group.findit.ui.startgame.StartGameRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Dagger module for binding interfaces to their implementations.
 * Provides dependency injection for the Start Game feature.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class StartGameBinderModule {

    /**
     * Binds the [StartGameRepositoryImpl] implementation to the [StartGameRepository] interface.
     * @param impl The implementation of [StartGameRepository].
     * @return The bound [StartGameRepository] instance.
     */
    @Binds
    abstract fun bindStartGameRepository(impl: StartGameRepositoryImpl): StartGameRepository

    /**
     * Binds the [StartGameDataSourceImpl] implementation to the [StartGameDataSource] interface.
     * @param impl The implementation of [StartGameDataSource].
     * @return The bound [StartGameDataSource] instance.
     */
    @Binds
    abstract fun bindStartGameDataSource(impl: StartGameDataSourceImpl): StartGameDataSource
}