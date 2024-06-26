package com.clone_coding.presentation.coininfo.coinnews

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clone_coding.domain.model.CoinInfoCoinNewsLatestNewsModel
import com.clone_coding.domain.repository.CoinInfoCoinNewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinInfoCoinNewsViewModel @Inject constructor(
    private val repository: CoinInfoCoinNewsRepository
) : ViewModel() {

    // 메인 뉴스에 들어갈 애들은 이 LiveData들을 바인딩하는게 맞다고 생각함.
    private val _mainNewsTitle = MutableLiveData<String>()
    val mainNewsTitle: LiveData<String>
        get() = _mainNewsTitle

    private val _mainNewsSummary = MutableLiveData<String>()
    val mainNewsSummary: LiveData<String>
        get() = _mainNewsSummary

    // 이렇게 map으로 받는 것 보단
    // private val _subNewsTitleMap = MutableLiveData<Map<String, String>>()
    // val subNewsTitleMap: LiveData<Map<String, String>>
    //   get() = _subNewsTitleMap

    private val _subNewsTitleFirst = MutableLiveData<String>()
    val subNewsTitleFirst: LiveData<String>
        get() = _subNewsTitleFirst

    private val _subNewsTitleSecond = MutableLiveData<String>()
    val subNewsTitleSecond: LiveData<String>
        get() = _subNewsTitleSecond

    // 코인 뉴스 크롤링 데이터
    private var _latestNewsList = MutableLiveData<MutableList<CoinInfoCoinNewsLatestNewsModel>>()
    val latestNewsList: LiveData<MutableList<CoinInfoCoinNewsLatestNewsModel>>
        get() = _latestNewsList

    fun getMainNews() {

        viewModelScope.launch {

            repository.getMainNews()

            _mainNewsTitle.value = repository.mainNewsTitle
            _mainNewsSummary.value = repository.mainNewsSummary
            //_subNewsTitleMap.value = repository.subNewsMap
            _subNewsTitleFirst.value = repository.subNewsMap.entries.firstOrNull()?.key
            _subNewsTitleSecond.value = repository.subNewsMap.entries.elementAtOrNull(1)?.key

            Log.d("coinNewsViewModel1", mainNewsTitle.value.toString())
            Log.d("coinNewsViewModel2", mainNewsSummary.value.toString())
            // Log.d("coinNewsViewModel3", subNewsTitleMap.value.toString())

        }

    }

    fun getLatestCoinNews() {

        viewModelScope.launch {

            repository.getLatestCoinNews()

            _latestNewsList.value = repository.coinNewsList

        }

    }

    fun getLatestPolicyNews() {

        viewModelScope.launch {

            repository.getLatestPolicyNews()

            _latestNewsList.value = repository.policyNewsList

        }

    }

    fun getLatestTechNews() {

        viewModelScope.launch {

            repository.getLatestTechNews()

            _latestNewsList.value = repository.techNewsList

        }

    }

    fun getLatestColumnNews() {

        viewModelScope.launch {

            repository.getLatestColumnNews()

            _latestNewsList.value = repository.columnNewsList

        }

    }

}