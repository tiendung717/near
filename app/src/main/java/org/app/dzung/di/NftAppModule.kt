package org.app.dzung.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.app.dzung.data.Repository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NftAppModule {
    @Provides
    @Singleton
    fun provideRepository() = Repository()
}