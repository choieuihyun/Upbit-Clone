package com.clone_coding.data.di

import com.clone_coding.data.datasource.remote_datasource.CoinInfoCoinNewsDatasource
import com.clone_coding.data.datasource.remote_datasource.CoinInfoCoinTrendDatasource
import com.clone_coding.data.datasource.remote_datasource.CoinInvestmentAssetHoldDatasource
import com.clone_coding.data.datasource.remote_datasource.UpbitTradeCenterDetailChartDatasource
import com.clone_coding.data.datasource.remote_datasource.CoinWithdrawDatasource
import com.clone_coding.data.datasource.remote_datasource.UpbitTradeCenterDatasource
import com.clone_coding.data.repository.CoinInfoCoinNewsRepositoryImpl
import com.clone_coding.data.repository.CoinInfoCoinTrendRepositoryImpl
import com.clone_coding.data.repository.CoinInvestmentAssetHoldRepositoryImpl
import com.clone_coding.data.repository.CoinWithdrawRepositoryImpl
import com.clone_coding.data.repository.UpbitTradeCenterDetailChartRepositoryImpl
import com.clone_coding.data.repository.UpbitTradeCenterRepositoryImpl
import com.clone_coding.domain.error.NetworkErrorHandler
import com.clone_coding.domain.repository.CoinInfoCoinNewsRepository
import com.clone_coding.domain.repository.CoinInfoCoinTrendRepository
import com.clone_coding.domain.repository.CoinInvestmentAssetHoldRepository
import com.clone_coding.domain.repository.CoinWithdrawRepository
import com.clone_coding.domain.repository.UpbitTradeCenterDetailChartRepository
import com.clone_coding.domain.repository.UpbitTradeCenterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    // Provide로 도배한거 고쳐야함.

    @Singleton
    @Provides
    fun provideUpbitRepository(dataSource: UpbitTradeCenterDatasource) : UpbitTradeCenterRepository {
        return UpbitTradeCenterRepositoryImpl(dataSource)
    }

    @Singleton
    @Provides
    fun provideInvestmentAssetHoldRepository(datasource: CoinInvestmentAssetHoldDatasource, tradeCenterDatasource: UpbitTradeCenterDatasource) : CoinInvestmentAssetHoldRepository {
        return CoinInvestmentAssetHoldRepositoryImpl(datasource, tradeCenterDatasource)
    }

    @Singleton
    @Provides
    fun provideCoinInfoCoinTrendRepository(coinTrendDatasource: CoinInfoCoinTrendDatasource) : CoinInfoCoinTrendRepository {
        return CoinInfoCoinTrendRepositoryImpl(coinTrendDatasource)
    }

    @Singleton
    @Provides
    fun provideCoinInfoCoinNewsRepository(coinNewsDatasource: CoinInfoCoinNewsDatasource) : CoinInfoCoinNewsRepository {
        return CoinInfoCoinNewsRepositoryImpl(coinNewsDatasource)
    }

    @Singleton
    @Provides
    fun provideCoinWithdrawRepository(coinWithdrawDatasource: CoinWithdrawDatasource, networkHandler: NetworkErrorHandler) : CoinWithdrawRepository {
        return CoinWithdrawRepositoryImpl(coinWithdrawDatasource, networkHandler)
    }

    @Singleton
    @Provides
    fun provideTradeCenterDetailChartRepository(tradeCenterDetailChartDatasource: UpbitTradeCenterDetailChartDatasource) : UpbitTradeCenterDetailChartRepository {
        return UpbitTradeCenterDetailChartRepositoryImpl(tradeCenterDetailChartDatasource)
    }

}