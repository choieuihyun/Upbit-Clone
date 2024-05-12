package com.clone_coding.data.repository

import com.clone_coding.data.datasource.remote_datasource.UpbitTradeCenterDetailChartDatasource
import com.clone_coding.data.mapper.mapNetworkResult
import com.clone_coding.data.mapper.toModel
import com.clone_coding.domain.error.NetworkResult
import com.clone_coding.domain.model.TradeCenterDetailChartModel
import com.clone_coding.domain.repository.UpbitTradeCenterDetailChartRepository
import javax.inject.Inject

class UpbitTradeCenterDetailChartRepositoryImpl @Inject constructor(
    private val dataSource: UpbitTradeCenterDetailChartDatasource
): UpbitTradeCenterDetailChartRepository {

    override suspend fun getTradeCenterChartInformation(
        market: String,
        to: String,
        count: Int,
        convertingPriceUnit: String
    ): NetworkResult<List<TradeCenterDetailChartModel>?> {

        return dataSource.getTradeCenterChartInformation(
            market,
            to,
            count,
            convertingPriceUnit).mapNetworkResult { list ->
            list?.map {
                it.toModel()
            }
        }

    }

}