package com.clone_coding.data.repository

import android.util.Log
import com.clone_coding.data.datasource.remote_datasource.CoinWithdrawDatasource
import com.clone_coding.data.mapper.toNetworkResult
import com.clone_coding.domain.error.NetworkErrorHandler
import com.clone_coding.domain.error.NetworkResult
import com.clone_coding.domain.model.CoinWithdrawCoinListModel
import com.clone_coding.domain.repository.CoinWithdrawRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject
import kotlin.NullPointerException

class CoinWithdrawRepositoryImpl @Inject constructor(
    private val coinWithdrawDatasource: CoinWithdrawDatasource,
    private val networkErrorHandler: NetworkErrorHandler,
) : CoinWithdrawRepository {

    override suspend fun getWithdrawList(): NetworkResult<List<CoinWithdrawCoinListModel>> {

        // val coinList = coinWithdrawDatasource.getCoinList()
        // val holdAsset = coinWithdrawDatasource.getHoldAsset()
        //val currentPriceList = coinTradeCenterDatasource.getTradeCenterCurrentPrice()

//        withContext(Dispatchers.IO) {
//
//            val coinListDeferred = async { coinWithdrawDatasource.getCoinList() }
//            val holdAssetDeferred = async { coinWithdrawDatasource.getHoldAsset() }
//
//            val a = coinListDeferred.await()
//            val b = holdAssetDeferred.await()
//
//        }



        // 데이터를 보니까 AssetHold의 currency와 AllData의 symbol의 대문자 치환이 같을 경우로 하면 된다고 판단.
        // CoinWithdrawCoinListModel에 AssetHold의 값을 넣어주고 반환. 이게 맞지 않냐?

        val result = withContext(Dispatchers.IO) {

            val mergedResult = mutableListOf<CoinWithdrawCoinListModel>()

            val coinListAsync = async { coinWithdrawDatasource.getCoinList() }
            val holdAssetAsync = async { coinWithdrawDatasource.getHoldAsset() }

            val coinListResult = coinListAsync.await().toNetworkResult()
            val holdAssetResult = holdAssetAsync.await().toNetworkResult()

            try {

                for (holdAssetItem in holdAssetResult!!) {

                    val currencyLowerCase = holdAssetItem.currency.lowercase(Locale.getDefault())

                    for (coinListItem in coinListResult) {

                        if (currencyLowerCase == coinListItem.symbol) {

                            val holdAssetModel = CoinWithdrawCoinListModel(
                                symbol = coinListItem.symbol,
                                currentPrice = coinListItem.currentPrice.toString(),
                                name = coinListItem.name,
                                imageUrl = coinListItem.image,
                                coinTotalPrice = (holdAssetItem.avgBuyPrice.toDouble() * holdAssetItem.balance.toDouble()).toString(),
                                coinBalance = holdAssetItem.balance
                            )

                            mergedResult.add(holdAssetModel)

                        }

                    }

                }

            } catch (e: NullPointerException) {

                Log.e("NPEWithDrawImpl", "NPE")

            }

            for (coinListItem in coinListResult) {

                val notHoldAssetModel = CoinWithdrawCoinListModel(
                    symbol = coinListItem.symbol,
                    currentPrice = coinListItem.currentPrice.toString(),
                    name = coinListItem.name,
                    imageUrl = coinListItem.image,
                    coinTotalPrice = "0",
                    coinBalance = "0"
                )

                if (!mergedResult.any { it.symbol == notHoldAssetModel.symbol }) {

                    mergedResult.add(notHoldAssetModel)

                }

            }

            mergedResult

        }

        // 여기서 왜 다시 감쌌냐면, datasource에서 받아온 것들을 한 번 벗겨내서 다시 감싸서 전달하기 위해.
        return try {

            Log.d("withdrawRepoImplMerged", result.toString())
            NetworkResult.Success(result)

        } catch (e: Exception) {

            val errorType = networkErrorHandler.errorMessage(e)
            NetworkResult.Error(errorType)

        }

    }

//    override suspend fun getWithdrawList(): NetworkResult<List<CoinWithdrawCoinListModel>> {
//
//        val coinList = coinWithdrawDatasource.getCoinList()
//        val holdAsset = coinWithdrawDatasource.getHoldAsset()
//        //val currentPriceList = coinTradeCenterDatasource.getTradeCenterCurrentPrice()
//
////        withContext(Dispatchers.IO) {
////
////            val coinListDeferred = async { coinWithdrawDatasource.getCoinList() }
////            val holdAssetDeferred = async { coinWithdrawDatasource.getHoldAsset() }
////
////            val a = coinListDeferred.await()
////            val b = holdAssetDeferred.await()
////
////        }
//
//        val coinListResult = coinList.toNetworkResult()
//        val holdAssetResult = holdAsset.toNetworkResult()
//        val mergedResult = mutableListOf<CoinWithdrawCoinListModel>()
//
//        // 데이터를 보니까 AssetHold의 currency와 AllData의 symbol의 대문자 치환이 같을 경우로 하면 된다고 판단.
//        // CoinWithdrawCoinListModel에 AssetHold의 값을 넣어주고 반환. 이게 맞지 않냐?
//
//        Log.d("withdrawRepoHoldAsset1", holdAsset.toNetworkResult().toString())
//        Log.d("withdrawRepoCoinList2", coinList.toNetworkResult().toString())
//
//        try {
//            // null일 가능성도 있다고 본다.(내가 정적 리스트로 호출했기 때문.) 처리 제대로 해줘야함.
//            // 보유한 코인들
//            for (holdAssetItem in holdAssetResult!!) {
//
//                // AssetHold의 currency의 소문자 == AllDataResponse의 symbol
//                val currencyLowerCase = holdAssetItem.currency.lowercase(Locale.getDefault())
//
//                for (coinListItem in coinListResult) {
//
//                    if (currencyLowerCase == coinListItem.symbol) {
//
//                        // 1. 이대로 Model로 생성해서 넘기고 이 안에서 내부 데이터 계산
//                        // 2. Response를 하나 새로 만들고 매퍼에서 모델로 매핑하는 메서드 만든 후,
//                        //    그 메서드 안에서 다 계산하는 방식으로 매퍼 생성하여 변환 후 반환.
//                        val holdAssetModel = CoinWithdrawCoinListModel(
//                            symbol = coinListItem.symbol,
//                            currentPrice = coinListItem.currentPrice.toString(),
//                            name = coinListItem.name,
//                            imageUrl = coinListItem.image,
//                            coinTotalPrice = (holdAssetItem.avgBuyPrice.toDouble() * holdAssetItem.balance.toDouble()).toString(),
//                            coinBalance = holdAssetItem.balance
//                        )
//
//                        mergedResult.add(holdAssetModel)
//
//                    }
//
//                }
//
//            }
//        } catch (e: NullPointerException) {
//
//        }
//
//        // 보유하지 않은 코인들
//        for (coinListItem in coinListResult) {
//            // 1. 그냥 이대로 Model로 생성해서 넘기고 이 안에서 내부 데이터 계산
//            // 2. Response를 하나 새로 만들고 매퍼에서 모델로 매핑하는 메서드 만든 후,
//            //    그 메서드 안에서 다 계산하는 방식으로 매퍼 생성하여 변환 후 반환.
//            val notHoldAssetModel = CoinWithdrawCoinListModel(
//                symbol = coinListItem.symbol,
//                currentPrice = coinListItem.currentPrice.toString(),
//                name = coinListItem.name,
//                imageUrl = coinListItem.image,
//                coinTotalPrice = "0",
//                coinBalance = "0"
//            )
//
//            if (!mergedResult.any { it.symbol == notHoldAssetModel.symbol }) {
//                mergedResult.add(notHoldAssetModel)
//            }
//
//        }
//
//
//        // 여기서 왜 다시 감쌌냐면, datasource에서 받아온 것들을 한 번 벗겨내서 다시 감싸서 전달하기 위해.
//        return try {
//
//            Log.d("withdrawRepoImplMerged", mergedResult.toString())
//            NetworkResult.Success(mergedResult)
//
//        } catch (e: Exception) {
//
//            val errorType = networkErrorHandler.errorMessage(e)
//            NetworkResult.Error(errorType)
//
//        }
//
//    }

}