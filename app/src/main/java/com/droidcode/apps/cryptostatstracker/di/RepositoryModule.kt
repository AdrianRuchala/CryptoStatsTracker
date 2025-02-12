package com.droidcode.apps.cryptostatstracker.di

import com.droidcode.apps.cryptostatstracker.data.remote.CryptoApi
import com.droidcode.apps.cryptostatstracker.data.repository.CryptoRepositoryImpl
import com.droidcode.apps.cryptostatstracker.domain.repository.CryptoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideCryptoRepository(api: CryptoApi): CryptoRepository {
        return CryptoRepositoryImpl(api)
    }
}
