package com.clone_coding.domain.repository

import android.net.Network
import com.clone_coding.domain.error.NetworkResult
import com.clone_coding.domain.model.CoinWithdrawCoinListModel
import com.clone_coding.domain.model.CoinWithdrawModel
import com.clone_coding.domain.model.MarketCapListModel

interface CoinWithdrawRepository {

    //suspend fun getWithdrawList(): NetworkResult<MutableList<CoinWithdrawModel>>
    suspend fun getWithdrawList(): NetworkResult<List<CoinWithdrawCoinListModel>>

    suspend fun getCoinList(): NetworkResult<List<CoinWithdrawCoinListModel>>

}