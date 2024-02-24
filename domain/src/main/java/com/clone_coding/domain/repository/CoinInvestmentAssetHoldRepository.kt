package com.clone_coding.domain.repository

import com.clone_coding.domain.error.NetworkResult
import com.clone_coding.domain.model.CoinInvestmentAssetHoldModel

interface CoinInvestmentAssetHoldRepository {

    suspend fun getInvestmentAssetHold() : NetworkResult<List<CoinInvestmentAssetHoldModel?>>

}