package com.clone_coding.data.datasource.remote_datasource

import android.util.Log
import com.clone_coding.data.db.remote.response.coin_news_response.CoinInfoCoinNewsLatestNewsResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

import javax.inject.Inject

class CoinInfoCoinNewsDatasource @Inject constructor() {

    // 메서드에 리턴 타입을 넘기고 그 메서드를 다른 레이어에서 또 사용하는 것 보단
    // 이렇게 하는 구조가 개인적으로는 깔끔하다고 생각함.

    // 메인 뉴스 크롤링 데이터
    private var _mainNewsTitle = ""
    val mainNewsTitle: String
        get() = _mainNewsTitle

    private var _mainNewsSummary = ""
    val mainNewsSummary: String
        get() = _mainNewsSummary

    private var _mainNewsHref = ""
    val mainNewsHref: String
        get() = _mainNewsHref

    private val _subNewsMap = mutableMapOf<String, String>()
    val subNewsMap: Map<String, String>
        get() = _subNewsMap

    // 코인 뉴스 크롤링 데이터
    private var _coinNewsList = mutableListOf<CoinInfoCoinNewsLatestNewsResponse>()
    val coinNewsList: MutableList<CoinInfoCoinNewsLatestNewsResponse>
        get() = _coinNewsList

    // 정책 뉴스 크롤링 데이터
    private var _policyNewsList = mutableListOf<CoinInfoCoinNewsLatestNewsResponse>()
    val policyNewsList: MutableList<CoinInfoCoinNewsLatestNewsResponse>
        get() = _policyNewsList

    // 테크 뉴스 크롤링 데이터
    private var _techNewsList = mutableListOf<CoinInfoCoinNewsLatestNewsResponse>()
    val techNewsList: MutableList<CoinInfoCoinNewsLatestNewsResponse>
        get() = _techNewsList

    // 칼럼 뉴스 크롤링 데이터
    private var _columnNewsList = mutableListOf<CoinInfoCoinNewsLatestNewsResponse>()
    val columnNewsList: MutableList<CoinInfoCoinNewsLatestNewsResponse>
        get() = _columnNewsList

    // 주요 뉴스 크롤링
    suspend fun getMainNews() {

        withContext(Dispatchers.IO) {

            val document =
                Jsoup.connect("https://www.blockmedia.co.kr/archives/category/chaincoin/business")
                    .get()

            // id가 main-content인 요소 선택
            val mainContent = document.getElementById("main-content")

            // post 클래스를 가진 모든 article 요소 선택
            val articles = mainContent.getElementsByClass("post")

            for (i in 0 until 3) {

                val article = articles[i]

                // 요 두개는 주요 뉴스 공통
                val title = article.getElementsByClass("post-title").first().text()

                val href = article.getElementsByClass("post-title").first().select("a").attr("href")

                if (i == 0) { // 주요 뉴스에서 위의 제목, 요약

                    // 요 놈은 주요 뉴스에만 포함
                    val summary = article.getElementsByClass("excerpt").first().text()

                    _mainNewsTitle = title
                    _mainNewsSummary = summary
                    _mainNewsHref = href
                    Log.d("ssss", mainNewsTitle.toString())

                } else {

                    // 주요 뉴스에서 아래 부분의 제목 두 개
                    _subNewsMap[title] = href

                }
            }
        } // async 블록을 사용해서 완료되는걸 기다렸다가 뷰에 띄워주도록 하거나 withContext를 사용해야함.

    }

    // 코인 뉴스 크롤링
    suspend fun getLatestCoinNews() { // 이 4개는 묶으면 안된다고 생각함. 하나 호출 할 때 마다 4개씩 호출하고싶냐?

        withContext(Dispatchers.IO) {

            _coinNewsList = getLatestNews("https://www.blockmedia.co.kr/archives/category/coinness")

            //Log.d("ssssCoinNews", coinNewsMap.toString())

        }

    }

    suspend fun getLatestPolicyNews() {

        withContext(Dispatchers.IO)  {

            _policyNewsList = getLatestNews("https://www.blockmedia.co.kr/archives/category/policy")

        }

    }

    suspend fun getLatestTechNews() {

        withContext(Dispatchers.IO)  {

            _techNewsList = getLatestNews("https://www.blockmedia.co.kr/archives/category/chaincoin/tech")

        }

    }

    suspend fun getLatestColumnNews() {

        withContext(Dispatchers.IO)  {

            _columnNewsList = getLatestNews("https://www.blockmedia.co.kr/archives/category/for-u/opinion")

        }

    }


    // 최신 뉴스 크롤링 메서드
    private fun getLatestNews(url: String) : MutableList<CoinInfoCoinNewsLatestNewsResponse> {

        val document =
            Jsoup.connect(url).get()

        // id가 main-content인 요소 선택
        val mainContent = document.getElementById("main-content")

        // post 클래스를 가진 모든 article 요소 선택
        val articles = mainContent.getElementsByClass("post")

        val newsList = mutableListOf<CoinInfoCoinNewsLatestNewsResponse>()

        //val newsMap = mutableMapOf<String, String>()

        for (article in articles) {

            val title = article.getElementsByClass("post-title").first().text()

            val href = article.getElementsByClass("post-title").first().select("a").attr("href")

            val time = "시간"

            val latestNews = CoinInfoCoinNewsLatestNewsResponse(
                title,
                href,
                time
            )

            newsList.add(latestNews)
            //newsMap[title] = href

        }

        return newsList
    }
}