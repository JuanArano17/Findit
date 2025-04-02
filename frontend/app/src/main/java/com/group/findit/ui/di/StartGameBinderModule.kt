package com.group.findit.ui.di

import com.group.findit.ui.data.startgame.StartGameDataSource
import com.group.findit.ui.data.startgame.StartGameDataSourceImpl
import com.group.findit.ui.startgame.StartGameRepository
import com.group.findit.ui.startgame.StartGameRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module @InstallIn(SingletonComponent::class)
abstract class StartGameBinderModule {
    @Binds
    abstract fun bindStartGameRepository(impl: StartGameRepositoryImpl) : StartGameRepository
    @Binds
    abstract fun bindStartGameDataSource(impl: StartGameDataSourceImpl) : StartGameDataSource
}