package com.clone_coding.data.datasource.remote_datasource

import android.util.Log
import androidx.lifecycle.LiveData
import com.clone_coding.data.db.remote.api.UpbitApi
import com.clone_coding.data.db.remote.response.TradeCenterKRWTabInitializePriceResponse
import com.clone_coding.data.db.remote.response.TradeCenterKRWTabMarketListResponse
import com.clone_coding.data.di.YourWebSocketClient
import com.clone_coding.data.di.YourWebSocketListener
import com.clone_coding.domain.error.NetworkErrorHandler
import com.clone_coding.domain.error.NetworkResult
import com.google.gson.Gson
import okhttp3.Request
import okhttp3.WebSocket
import java.lang.Exception
import javax.inject.Inject

class UpbitTradeCenterDatasource @Inject constructor(
    private val webSocketClient: YourWebSocketClient,
    private val webSocketListener: YourWebSocketListener,
    private val webSocketUrl: String,
    private val upbitApi: UpbitApi, // REST API
    private val networkErrorHandler: NetworkErrorHandler

) {

    // LiveData를 통해 WebSocket 데이터를 관찰
    val code: LiveData<String>
        get() = webSocketListener.code

    val tradePrice: LiveData<String>
        get() = webSocketListener.tradePrice

    val signedChangeRate: LiveData<String>
        get() = webSocketListener.signedChangeRate

    val accTradePrice24H: LiveData<String>
        get() = webSocketListener.accTradePrice24H

    private val gson = Gson()
    private lateinit var webSocket: WebSocket

    fun connectWebSocket() {

        val request: Request = Request.Builder()
            .url(webSocketUrl)
            .build()

        webSocket = webSocketClient.connectWebSocket(request, webSocketListener)
        webSocket.send(createTicket())

    }

    data class Ticket(val ticket: String)
    data class Type(val type: String, val codes: List<String>)

    private fun createTicket(): String {
        val ticket = Ticket("test")
        val codeList = arrayListOf(
            "KRW-BTC", "KRW-ETH", "KRW-NEO", "KRW-MTL", "KRW-XRP",
            "KRW-ETC", "KRW-SNT", "KRW-WAVES", "KRW-XEM", "KRW-QTUM", "KRW-LSK", "KRW-STEEM",
            "KRW-XLM", "KRW-ARDR", "KRW-ARK", "KRW-STORJ", "KRW-GRS", "KRW-ADA", "KRW-SBD",
            "KRW-POWR", "KRW-BTG", "KRW-ICX", "KRW-EOS", "KRW-TRX", "KRW-SC", "KRW-ONT",
            "KRW-ZIL", "KRW-POLYX", "KRW-ZRX", "KRW-LOOM", "KRW-BCH", "KRW-HIFI", "KRW-ONG",
            "KRW-GAS", "KRW-UPP", "KRW-ELF", "KRW-KNC", "KRW-THETA", "KRW-QKC", "KRW-BTT",
            "KRW-MOC", "KRW-TFUEL", "KRW-MANA", "KRW-ANKR", "KRW-AERGO", "KRW-ATOM", "KRW-TT",
            "KRW-CRE", "KRW-MBL", "KRW-WAXP", "KRW-HBAR", "KRW-MED", "KRW-STPT", "KRW-ORBS",
            "KRW-CHZ", "KRW-XTZ", "KRW-HIVE", "KRW-KAVA", "KRW-AHT", "KRW-LINK", "KRW-BORA",
            "KRW-JST", "KRW-CRO", "KRW-SXP", "KRW-HUNT", "KRW-TON", "KRW-PLA", "KRW-DOT",
            "KRW-MVL", "KRW-REI", "KRW-AQT", "KRW-STRAX", "KRW-GLM", "KRW-SSX", "KRW-META",
            "KRW-FCT2", "KRW-CBK", "KRW-SAND", "KRW-HPO", "KRW-DOGE", "KRW-XEC", "KRW-SOL",
            "KRW-MATIC", "KRW-AAVE", "KRW-1INCH", "KRW-FLOW", "KRW-AXS", "KRW-STX", "KRW-NEAR",
            "KRW-ALGO", "KRW-T", "KRW-CELO", "KRW-GMT", "KRW-APT", "KRW-SHIB", "KRW-MASK",
            "KRW-ARB", "KRW-EGLD", "KRW-SUI", "KRW-GRT", "KRW-BLUR", "KRW-IMX", "KRW-SEI",
            "KRW-MINA", "KRW-CTC", "KRW-ASTR", "KRW-ID"
        )
        val type = Type("ticker", codeList)

        return gson.toJson(arrayListOf(ticket, type))
    }

    // KRW Tab 데이터 뭉탱이 애초에 이 구조가 할 수가 없던거 아님? RESTful API는 한 번 받아오고 마는데 실시간 데이터를 이걸로 어떻게 하겠다는거?
    suspend fun getTradeCenterMarketList(): NetworkResult<TradeCenterKRWTabMarketListResponse> {

        val response = upbitApi.getMarketInformation()

        Log.d("dataSource", response.body().toString())

        val tradeCenterKRWTabData =
            TradeCenterKRWTabMarketListResponse(
                response.body()
            )

        // 어차피 네트워크 연결 에러가 나면 여기서 에러가 나니까 Response도 같이 처리?
        return try {
            NetworkResult.Success(tradeCenterKRWTabData)
        } catch (e: Exception) {
            val errorType = networkErrorHandler.errorMessage(e)
            NetworkResult.Error(errorType)
        }

    }

    // 현재가 정보
    suspend fun getTradeCenterCurrentPrice(): NetworkResult<List<TradeCenterKRWTabInitializePriceResponse>?> {

        val response = upbitApi.getCurrentPriceInformation("KRW-BTC, KRW-ETH, KRW-NEO, KRW-MTL, KRW-XRP," +
                " KRW-ETC, KRW-SNT, KRW-WAVES, KRW-XEM, KRW-QTUM, KRW-LSK," +
                " KRW-STEEM, KRW-XLM, KRW-ARDR, KRW-ARK, KRW-STORJ, KRW-GRS," +
                " KRW-ADA, KRW-SBD, KRW-POWR, KRW-BTG, KRW-ICX, KRW-EOS, KRW-TRX," +
                " KRW-SC, KRW-ONT, KRW-ZIL, KRW-POLYX, KRW-ZRX, KRW-LOOM, KRW-BCH," +
                " KRW-HIFI, KRW-ONG, KRW-GAS, KRW-UPP, KRW-ELF, KRW-KNC, KRW-THETA," +
                " KRW-QKC, KRW-BTT, KRW-MOC, KRW-TFUEL, KRW-MANA, KRW-ANKR, KRW-AERGO," +
                " KRW-ATOM, KRW-TT, KRW-CRE, KRW-MBL, KRW-WAXP, KRW-HBAR, KRW-MED," +
                " KRW-STPT, KRW-ORBS, KRW-CHZ, KRW-XTZ, KRW-HIVE, KRW-KAVA, KRW-AHT," +
                " KRW-LINK, KRW-BORA, KRW-JST, KRW-CRO, KRW-SXP, KRW-HUNT, KRW-TON," +
                " KRW-PLA, KRW-DOT, KRW-MVL, KRW-AQT, KRW-STRAX, KRW-GLM, KRW-SSX," +
                " KRW-META, KRW-FCT2, KRW-CBK, KRW-SAND, KRW-HPO, KRW-DOGE, KRW-XEC," +
                " KRW-SOL, KRW-MATIC, KRW-AAVE, KRW-1INCH, KRW-FLOW, KRW-AXS, KRW-STX," +
                " KRW-NEAR, KRW-ALGO, KRW-T, KRW-CELO, KRW-GMT, KRW-APT, KRW-SHIB," +
                " KRW-MASK, KRW-ARB, KRW-EGLD, KRW-SUI, KRW-GRT, KRW-BLUR, KRW-IMX," +
                " KRW-SEI, KRW-MINA, KRW-CTC, KRW-ASTR, KRW-ID")

        Log.d("dataSourceCurrentPrice", response.body().toString())

        // 어차피 네트워크 연결 에러가 나면 여기서 에러가 나니까 Response도 같이 처리?
        return try {
            NetworkResult.Success(response.body())
        } catch (e: Exception) {
            val errorType = networkErrorHandler.errorMessage(e)
            NetworkResult.Error(errorType)
        }

    }

}