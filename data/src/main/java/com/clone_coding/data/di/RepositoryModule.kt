package com.clone_coding.data.di

import com.clone_coding.data.datasource.remote_datasource.CoinInfoCoinTrendDatasource
import com.clone_coding.data.datasource.remote_datasource.UpbitTradeCenterDatasource
import com.clone_coding.data.repository.CoinInfoCoinTrendRepositoryImpl
import com.clone_coding.data.repository.UpbitTradeCenterRepositoryImpl
import com.clone_coding.domain.repository.CoinInfoCoinTrendRepository
import com.clone_coding.domain.repository.UpbitTradeCenterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideUpbitRepository(dataSource: UpbitTradeCenterDatasource) : UpbitTradeCenterRepository {
        return UpbitTradeCenterRepositoryImpl(dataSource)
    }

    @Singleton
    @Provides
    fun provideCoinInfoCoinTrendRepository(coinTrendDatasource: CoinInfoCoinTrendDatasource) : CoinInfoCoinTrendRepository {
        return CoinInfoCoinTrendRepositoryImpl(coinTrendDatasource)
    }

}