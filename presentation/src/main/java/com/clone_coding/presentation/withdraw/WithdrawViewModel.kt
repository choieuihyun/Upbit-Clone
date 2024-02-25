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

    private val _holdAsset = MutableLiveData<Int>()
    val holdAsset: LiveData<Int>
        get() = _holdAsset

    fun getWithDrawList() {

        viewModelScope.launch {

            var sumHoldAsset = 0

            when(val result = repository.getWithdrawList()) {

                is NetworkResult.Success -> {

                    Log.d("merged", result.data.toString())
                    _withdrawList.value = result.data

                }

                is NetworkResult.Error -> {

                    Log.d("error", result.errorType.toString())

                }

            }

        }

    }

}