package com.clone_coding.data.repository

import android.util.Log
import com.clone_coding.data.datasource.remote_datasource.CoinInvestmentAssetHoldDatasource
import com.clone_coding.data.datasource.remote_datasource.UpbitTradeCenterDatasource
import com.clone_coding.data.mapper.mapNetworkResult
import com.clone_coding.data.mapper.toModel
import com.clone_coding.data.mapper.toNetworkResult
import com.clone_coding.domain.error.NetworkResult
import com.clone_coding.domain.model.CoinInvestmentAssetHoldModel
import com.clone_coding.domain.repository.CoinInvestmentAssetHoldRepository
import javax.inject.Inject

class CoinInvestmentAssetHoldRepositoryImpl @Inject constructor(
    private val datasource: CoinInvestmentAssetHoldDatasource,
    private val tradeCenterDatasource: UpbitTradeCenterDatasource
) : CoinInvestmentAssetHoldRepository {

    override suspend fun getInvestmentAssetHold(): NetworkResult<List<CoinInvestmentAssetHoldModel?>> {

        val currentPriceList =
            tradeCenterDatasource.getTradeCenterCurrentPrice().toNetworkResult()

        return datasource.getInvestmentAssetHold().mapNetworkResult { investmentAssetList ->

            // mapNotNull은 null이 아닌 결과만 반환함
            investmentAssetList?.mapNotNull { asset ->

                val currentPrice = currentPriceList?.find {

                    val market = it.market.split("-")
                    market[1] == asset.currency

                }?.tradePrice.toString()

                // 그냥 currentPrice를 받아오면 거래소에 없는 코인들을 에어드랍으로 받은 적이 있어서 그 코인들이 currentPrice 값이 null이기 때문에
                // market명으로 받아올 수는 있어도 해당 tradePrice가 null이어서 위의 currentPrice가 "null"을 반환하게 됨.
                // 그래서 여기서 if문으로 한번 거르는것. + mapNotNull과 함께 사용해야함.
                // mapNotNull 함수는 람다 내에서 null을 반환하면 해당 요소는 결과에서 제외됨.
                if (currentPrice != "null") {
                    Log.d("investmentImpl1", currentPrice)
                    asset.toModel(currentPrice)
                } else {
                    Log.d("investmentImpl2", "null")
                    null
                }

            } ?: emptyList()

        }

    }

}