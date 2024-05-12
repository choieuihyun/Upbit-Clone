package com.clone_coding.domain.repository

import com.clone_coding.domain.error.NetworkResult
import com.clone_coding.domain.model.TradeCenterDetailChartModel

interface UpbitTradeCenterDetailChartRepository {

    suspend fun getTradeCenterChartInformation(
        market: String,
        to: String,
        count: Int,
        convertingPriceUnit: String)
    : NetworkResult<List<TradeCenterDetailChartModel>?>

}