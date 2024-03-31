package com.clone_coding.data.repository

import android.util.Log
import com.clone_coding.data.datasource.remote_datasource.CoinInfoCoinNewsDatasource
import com.clone_coding.data.mapper.toModel
import com.clone_coding.domain.model.CoinInfoCoinNewsLatestNewsModel
import com.clone_coding.domain.repository.CoinInfoCoinNewsRepository
import javax.inject.Inject

class CoinInfoCoinNewsRepositoryImpl @Inject constructor(
    private val datasource: CoinInfoCoinNewsDatasource
): CoinInfoCoinNewsRepository {

    override val mainNewsTitle: String
        get() = datasource.mainNewsTitle

    override val mainNewsSummary: String
        get() = datasource.mainNewsSummary

    override val subNewsMap: Map<String, String>
        get() = datasource.subNewsMap

    // 메서드에서 반환타입 설정 후 반환해도 되는데 왜 이렇게했냐?
    // 위의 구조대로 하다가 코루틴 실행 과정에서 순서를 못맞추게 되는 경우가 많았음 + 코드가 복잡했음.
    override val coinNewsList: MutableList<CoinInfoCoinNewsLatestNewsModel>
        get() = datasource.coinNewsList.map {
            it.toModel()
        }.toMutableList()

    override val policyNewsList: MutableList<CoinInfoCoinNewsLatestNewsModel>
        get() = datasource.policyNewsList.map {
            it.toModel()
        }.toMutableList()

    override val techNewsList: MutableList<CoinInfoCoinNewsLatestNewsModel>
        get() = datasource.techNewsList.map {
            it.toModel()
        }.toMutableList()

    override val columnNewsList: MutableList<CoinInfoCoinNewsLatestNewsModel>
        get() = datasource.columnNewsList.map {
            it.toModel()
        }.toMutableList()


    override suspend fun getMainNews() {

        datasource.getMainNews()

        Log.d("ssssImpl", mainNewsTitle)

    }

    override suspend fun getLatestCoinNews() {

        datasource.getLatestCoinNews()

    }

    override suspend fun getLatestPolicyNews() {

        datasource.getLatestPolicyNews()

    }

    override suspend fun getLatestTechNews() {

        datasource.getLatestTechNews()

    }

    override suspend fun getLatestColumnNews() {

        datasource.getLatestColumnNews()

    }

}