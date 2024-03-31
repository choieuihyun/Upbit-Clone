package com.clone_coding.presentation.withdraw

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clone_coding.domain.error.NetworkResult
import com.clone_coding.domain.model.CoinWithdrawCoinListModel
import com.clone_coding.domain.model.CoinWithdrawModel
import com.clone_coding.domain.repository.CoinWithdrawRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

@HiltViewModel
class WithdrawViewModel @Inject constructor(
    private val repository: CoinWithdrawRepository
) : ViewModel() {

    private val _withdrawList = MutableLiveData<List<CoinWithdrawCoinListModel>?>()
    val withdrawList: LiveData<List<CoinWithdrawCoinListModel>?>
        get() = _withdrawList

    private val _holdAsset = MutableLiveData<String>()
    val holdAsset: LiveData<String>
        get() = _holdAsset

    fun getWithDrawList() {

        viewModelScope.launch {

            var sumHoldAsset = 0
            val result = repository.getWithdrawList()

            when(result) {

                is NetworkResult.Success -> {

                    Log.d("merged", result.data.toString())

                    for(index in result.data.indices) {

                        val parseAsset = BigDecimal(result.data[index].coinTotalPrice.toDouble()).setScale(0, RoundingMode.DOWN).toInt()

                        sumHoldAsset += parseAsset

                    }

                    // 지금 이렇게 했는데 화면을 켰을 때 화면에 바로 데이터가 안뜸.
                    // 변화가 감지되지 않아서? 그렇다는건 내가 데이터를 불러올 때 무언가 문제가 있었다?
                    // 근데 그게 문제가 아니라 Livedata를 감지하는 건데 문제 없잖아.
                    _withdrawList.value = result.data

                    _holdAsset.value = sumHoldAsset.toString()


                    Log.d("withdrawViewModel", sumHoldAsset.toString())

                }

                is NetworkResult.Error -> {

                    Log.d("error", result.errorType.toString())

                }

            }

        }

//        viewModelScope.launch {
//
//            var sumHoldAsset = 0
//
//            when(val result = repository.getWithdrawList()) {
//
//                is NetworkResult.Success -> {
//
//                    Log.d("merged", result.data.toString())
//                    // 지금 이렇게 했는데 화면을 켰을 때 화면에 바로 데이터가 안뜸.
//                    // 변화가 감지되지 않아서? 그렇다는건 내가 데이터를 불러올 때 무언가 문제가 있었다?
//                    // 근데 그게 문제가 아니라 Livedata를 감지하는 건데 문제 없잖아.
//                    _withdrawList.value = result.data
//
//                    for(index in result.data.indices) {
//
//                        val parseAsset = BigDecimal(result.data[index].coinTotalPrice.toDouble()).setScale(0, RoundingMode.DOWN).toInt()
//
//                        sumHoldAsset += parseAsset
//
//                    }
//
//                    _holdAsset.value = sumHoldAsset.toString()
//
//                    Log.d("withdrawViewModel", sumHoldAsset.toString())
//
//                }
//
//                is NetworkResult.Error -> {
//
//                    Log.d("error", result.errorType.toString())
//
//                }
//
//            }
//
//        }

    }

}