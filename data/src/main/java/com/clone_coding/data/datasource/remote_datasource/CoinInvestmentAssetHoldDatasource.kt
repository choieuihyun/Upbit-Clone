package com.clone_coding.data.datasource.remote_datasource

import android.util.Log
import com.clone_coding.data.db.remote.api.UpbitJwtApi
import com.clone_coding.data.db.remote.response.coin_investment_asset_hold_response.CoinInvestmentAssetHoldResponse
import com.clone_coding.domain.error.NetworkErrorHandler
import com.clone_coding.domain.error.NetworkResult
import java.lang.Exception
import javax.inject.Inject

class CoinInvestmentAssetHoldDatasource @Inject constructor(
    private val upbitJwtApi: UpbitJwtApi,
    private val networkErrorHandler: NetworkErrorHandler
) {

    // 투자내역 화면 보유자산 탭 (내 보유자산 데이터)
    suspend fun getInvestmentAssetHold(): NetworkResult<List<CoinInvestmentAssetHoldResponse>?> {

        val response = upbitJwtApi.getInvestmentAssetHold()
        Log.d("investmentdatasource", response.body().toString())

        return try {

            NetworkResult.Success(response.body())

        } catch (e : Exception) {

            val errorType = networkErrorHandler.errorMessage(e)
            NetworkResult.Error(errorType)

        }

    }


}