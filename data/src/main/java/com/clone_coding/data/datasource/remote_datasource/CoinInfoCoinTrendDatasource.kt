package com.clone_coding.data.datasource.remote_datasource

import android.util.Log
import androidx.lifecycle.LiveData
import com.clone_coding.data.db.remote.api.CoinGeckoApi
import com.clone_coding.data.db.remote.api.UpbitApi
import com.clone_coding.data.db.remote.response.coingecko_api_response.CoinAllDataResponse
import com.clone_coding.data.db.remote.response.upbit_api_response.CoinInfoCoinTrendHighestIncreaseRateResponse
import com.clone_coding.data.di.CoinInfoCoinTrendWebSocketListener
import com.clone_coding.data.di.WebSocketClient
import com.clone_coding.data.di.WebSocketTicket
import com.clone_coding.domain.error.NetworkErrorHandler
import com.clone_coding.domain.error.NetworkResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Request
import okhttp3.WebSocket
import javax.inject.Inject

class CoinInfoCoinTrendDatasource @Inject constructor(
    private val upbitApi: UpbitApi,
    private val coinGeckoAPi: CoinGeckoApi,
    private val networkErrorHandler: NetworkErrorHandler,
    private val webSocketListener: CoinInfoCoinTrendWebSocketListener,
    private val webSocketUrl: String,
    private val webSocketClient: WebSocketClient
    ) {

    // 이걸 동적으로 바꿔야겠는데
    private val krwMarketList = listOf("KRW-BTC", "KRW-ETH", "KRW-NEO", "KRW-MTL", "KRW-XRP",
        "KRW-ETC", "KRW-SNT", "KRW-WAVES", "KRW-XEM", "KRW-QTUM", "KRW-LSK", "KRW-STEEM",
        "KRW-XLM", "KRW-ARDR", "KRW-ARK", "KRW-STORJ", "KRW-GRS", "KRW-PUNDIX",
        "KRW-ADA", "KRW-SBD", "KRW-POWR", "KRW-BTG", "KRW-ICX", "KRW-EOS", "KRW-TRX",
        "KRW-SC", "KRW-ONT", "KRW-ZIL", "KRW-POLYX", "KRW-ZRX", "KRW-LOOM", "KRW-BCH",
        "KRW-HIFI", "KRW-ONG", "KRW-GAS", "KRW-UPP", "KRW-ELF", "KRW-KNC", "KRW-THETA",
        "KRW-QKC", "KRW-BTT", "KRW-MOC", "KRW-TFUEL", "KRW-MANA", "KRW-ANKR", "KRW-AERGO",
        "KRW-ATOM", "KRW-TT", "KRW-CRE", "KRW-MBL", "KRW-WAXP", "KRW-HBAR", "KRW-MED",
        "KRW-STPT", "KRW-ORBS", "KRW-CHZ", "KRW-XTZ", "KRW-HIVE", "KRW-KAVA", "KRW-AHT",
        "KRW-LINK", "KRW-BORA", "KRW-JST", "KRW-CRO", "KRW-SXP", "KRW-HUNT", "KRW-TON",
        "KRW-PDA", "KRW-DOT", "KRW-MVL", "KRW-AQT", "KRW-STRAX", "KRW-GLM",
        "KRW-META", "KRW-FCT2", "KRW-CBK", "KRW-SAND", "KRW-HPO", "KRW-DOGE", "KRW-XEC",
        "KRW-SOL", "KRW-MATIC", "KRW-AAVE", "KRW-1INCH", "KRW-FLOW", "KRW-AXS", "KRW-STX",
        "KRW-NEAR", "KRW-ALGO", "KRW-T", "KRW-CELO", "KRW-GMT", "KRW-APT", "KRW-SHIB",
        "KRW-MASK", "KRW-ARB", "KRW-EGLD", "KRW-SUI", "KRW-GRT", "KRW-BLUR", "KRW-IMX",
        "KRW-SEI", "KRW-MINA", "KRW-CTC", "KRW-ASTR", "KRW-ID"
    )

    // 이것도 동적으로..
    private val krwMarketCoinNameList = listOf(
        "bitcoin", "ethereum", "neo", "metal", "ripple", "ethereum-classic", "status",
        "waves", "nem", "qtum", "lisk", "steem", "stellar", "ardor", "ark", "storj",
        "groestlcoin", "cardano", "steem-dollars", "power-ledger", "bitcoin-gold", "icon",
        "eos", "tron", "siacoin", "ontology", "zilliqa", "polymath", "0x", "loom-network",
        "bitcoin-cash", "hyprr", "ontology-gas", "gas", "sentinel-protocol", "aelf",
        "kyber-network", "theta-token", "quarkchain", "bittorrent", "mossland",
        "theta-fuel", "decentraland", "ankr", "aergo", "cosmos", "trust-token",
        "carry", "moviebloc", "wax", "hedera-hashgraph", "medibloc", "standard-tokenization-protocol",
        "orbs", "chiliz", "tezos", "hive", "kava", "a-hit", "chainlink", "bora", "just",
        "crypto-com", "swipe", "hunt-token", "tokamak-network", "playchip", "polkadot",
        "mass-vehicle-ledger", "alpha-quadrant", "stratis", "golem", "shookstake", "metadium",
        "firma-chain", "covid-blood-kills", "the-sandbox", "hyper-pay", "dogecoin", "eternal-cash",
        "solana", "polygon", "aave", "1inch", "flow", "axie-infinity", "stacks", "near-protocol",
        "algorand", "tomochain", "celo", "global-markets-token", "alpha-platform", "shiba-inu",
        "mask-network", "arbitrum", "elrond", "swissborg", "the-graph", "blur-network",
        "immutable-x", "seigniorage-shares", "mina-protocol", "culture-ticket-chain", "astral-protocol",
        "everest", "pundix"
    )

    val code: LiveData<String>
        get() = webSocketListener.code

    val accAskVolume: LiveData<String>
        get() = webSocketListener.accAskVolume

    val accBidVolume: LiveData<String>
        get() = webSocketListener.accBidVolume


    private lateinit var webSocket: WebSocket

    fun connectWebSocket() {

        CoroutineScope(Dispatchers.IO).launch {

            val request: Request = Request.Builder()
                .url(webSocketUrl)
                .build()

            webSocket = webSocketClient.connectWebSocket(request, webSocketListener)
            webSocket.send(WebSocketTicket.createTicket())

        }

    }

    suspend fun getCoinInfoCoinTrendHighestIncreaseRate(): NetworkResult<MutableList<List<CoinInfoCoinTrendHighestIncreaseRateResponse>?>> {

        val chunkedKrwMarketList = krwMarketList.chunked(krwMarketList.size / 10)

        val responseList = mutableListOf<List<CoinInfoCoinTrendHighestIncreaseRateResponse>?>()

        coroutineScope {

            chunkedKrwMarketList.forEach { chunk ->

                val response = chunk.map { market ->
                    async {

                        delay(1000)
                        upbitApi.getCoinInfoTrendHighestIncreaseRateWeek(market, 1) // count를 1로 하고 해당 데이터 내에서 계산 가능하잖아.

                    }
                }.awaitAll()

                responseList.addAll(response.map { it.body() })

            }

        }

        return try {
            NetworkResult.Success(responseList)
        } catch (e: Exception) {
            val networkError = networkErrorHandler.errorMessage(e)
            NetworkResult.Error(networkError)
        }
    }

    // 이게 진짜 쓰레기 코드라고 생각한다.
    // 반환 타입에 Response가
    suspend fun getCoinMarketCapList(): NetworkResult<MutableList<List<CoinAllDataResponse>?>> {

        //val chunkedKrwMarketList = krwMarketCoinNameList.chunked(krwMarketList.size / 10)

        val responseList = mutableListOf<List<CoinAllDataResponse>?>()

        coroutineScope {

            val response = withContext(Dispatchers.IO) {

                coinGeckoAPi.getCoinAllDataList(
                    "krw",
                    krwMarketCoinNameList.joinToString(","),
                    "market_cap_desc",
                    "100",
                    "1",
                    "7d",
                    "ko"
                )

            }

            // 올바른 구조가 아니여라.
            responseList.add(response.body())
            Log.d("responseList", responseList.size.toString())

        }

        return try {
            NetworkResult.Success(responseList)
        } catch (e: Exception) {
            val networkError = networkErrorHandler.errorMessage(e)
            NetworkResult.Error(networkError)
        }
    }

}