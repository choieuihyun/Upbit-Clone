package com.clone_coding.presentation.investment.assethold

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clone_coding.domain.error.NetworkResult
import com.clone_coding.domain.model.CoinInvestmentAssetHoldModel
import com.clone_coding.domain.repository.CoinInvestmentAssetHoldRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

@HiltViewModel
class InvestmentDetailAssetholdViewModel @Inject constructor(
    private val repository: CoinInvestmentAssetHoldRepository
): ViewModel() {

    private val _assetHoldList = MutableLiveData<List<CoinInvestmentAssetHoldModel?>?>()
    val assetHoldList: LiveData<List<CoinInvestmentAssetHoldModel?>?>
        get() = _assetHoldList

    // 보유 KRW
    private val _holdKRW = MutableLiveData<Int>()
    val holdKRW: LiveData<Int>
        get() = _holdKRW

    // 총 보유자산
    private val _holdAsset = MutableLiveData<Int>()
    val holdAsset: LiveData<Int>
        get() = _holdAsset


    // 죄다 String으로 할 필요가 있나?

    // 총매수
    private val _totalPurchase = MutableLiveData<String>()
    val totalPurchase: LiveData<String>
        get() = _totalPurchase

    // 총평가
    private val _totalEvaluation = MutableLiveData<String>()
    val totalEvaluation: LiveData<String>
        get() = _totalEvaluation

    // 평가손익
    private val _evaluationProfitLoss = MutableLiveData<Int>()
    val evaluationProfitLoss: LiveData<Int>
        get() = _evaluationProfitLoss

    // 수익률
    private val _profitLossPercentage = MutableLiveData<Double>()
    val profitLossPercentage: LiveData<Double>
        get() = _profitLossPercentage

    // 양전 음전에 따른 색상 변경 (빨 / 파)
    private val _profitLossTextColor: MutableLiveData<Int> = MutableLiveData()
    val profitLossTextColor: LiveData<Int>
        get() = _profitLossTextColor

    fun getInvestmentAssetHold() {

        viewModelScope.launch {

            when(val assetHold = repository.getInvestmentAssetHold()) {
                is NetworkResult.Success -> {

                    _assetHoldList.value = assetHold.data

                    var totalPurchase = 0
                    var totalEvaluation = 0
                    var evaluationProfitLoss = 0
                    var profitLossPercentage = 0.0
                    var holdKRW = 0.0
                    var holdAsset = 0

                    // 여기서 indices!!를 사용해도 되는건가? 보유자산 없으면 null이잖아.
                    for(index in assetHoldList.value?.indices!!) {

                        Log.d("investmentViewModel2", assetHoldList.value!![index].toString())

                        if(assetHoldList.value!![index]?.currency == "KRW") {

                            // 보유 KRW
                            holdKRW += assetHoldList.value!![index]?.balance?.toDouble() ?: 0.0 // KRW는 화폐니까 그 양만큼?

                        }


                        totalPurchase += assetHoldList.value!![index]?.totalPurchase?.toInt() ?: 0
                        totalEvaluation += assetHoldList.value!![index]?.totalEvaluation?.toInt() ?: 0
                        evaluationProfitLoss += assetHoldList.value!![index]?.evaluationProfitLoss?.toInt()
                            ?: 0

                    }
                    holdAsset = totalPurchase + evaluationProfitLoss + holdKRW.toInt()
                    profitLossPercentage =
                        BigDecimal((evaluationProfitLoss.toDouble() / totalPurchase.toDouble()) * 100)
                            .setScale(2, RoundingMode.DOWN)
                            .toDouble()

                    _totalPurchase.value = totalPurchase.toString()
                    _totalEvaluation.value = totalEvaluation.toString()
                    _evaluationProfitLoss.value = evaluationProfitLoss
                    _profitLossPercentage.value = profitLossPercentage

                    _holdAsset.value = holdAsset
                    _holdKRW.value = holdKRW.toInt()


                    Log.d("investmentViewModel", assetHold.data.toString())
                    Log.d("investmentViewModelTotalPurchase", profitLossPercentage.toString())
                }
                is NetworkResult.Error -> {
                    Log.e("assetHoldNetworkError", assetHold.errorType.toString())
                }
            }
        }
    }

}