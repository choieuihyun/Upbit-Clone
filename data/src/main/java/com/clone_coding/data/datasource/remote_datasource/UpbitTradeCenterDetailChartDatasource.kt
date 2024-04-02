package com.clone_coding.data.datasource.remote_datasource

import com.clone_coding.data.db.remote.api.UpbitApi
import com.clone_coding.data.db.remote.response.upbit_api_response.TradeCenterDetailChartResponse
import com.clone_coding.domain.error.NetworkErrorHandler
import com.clone_coding.domain.error.NetworkResult
import javax.inject.Inject

class UpbitTradeCenterDetailChartDatasource @Inject constructor(
    private val api: UpbitApi,
    private val networkErrorHandler: NetworkErrorHandler
) {
    private val krwMarketList = listOf("KRW-BTC", "KRW-ETH", "KRW-NEO", "KRW-MTL", "KRW-XRP",
        "KRW-ETC", "KRW-SNT", "KRW-WAVES", "KRW-XEM", "KRW-QTUM", "KRW-LSK", "KRW-STEEM",
        "KRW-XLM", "KRW-ARDR", "KRW-ARK", "KRW-STORJ", "KRW-GRS",
        "KRW-ADA", "KRW-SBD", "KRW-POWR", "KRW-BTG", "KRW-ICX", "KRW-EOS", "KRW-TRX",
        "KRW-SC", "KRW-ONT", "KRW-ZIL", "KRW-POLYX", "KRW-ZRX", "KRW-LOOM", "KRW-BCH",
        "KRW-HIFI", "KRW-ONG", "KRW-GAS", "KRW-UPP", "KRW-ELF", "KRW-KNC", "KRW-THETA",
        "KRW-QKC", "KRW-BTT", "KRW-MOC", "KRW-TFUEL", "KRW-MANA", "KRW-ANKR", "KRW-AERGO",
        "KRW-ATOM", "KRW-TT", "KRW-CRE", "KRW-MBL", "KRW-WAXP", "KRW-HBAR", "KRW-MED",
        "KRW-STPT", "KRW-ORBS", "KRW-CHZ", "KRW-XTZ", "KRW-HIVE", "KRW-KAVA", "KRW-AHT",
        "KRW-LINK", "KRW-BORA", "KRW-JST", "KRW-CRO", "KRW-SXP", "KRW-HUNT", "KRW-TON",
        "KRW-PLA", "KRW-DOT", "KRW-MVL", "KRW-AQT", "KRW-STRAX", "KRW-GLM",
        "KRW-META", "KRW-FCT2", "KRW-CBK", "KRW-SAND", "KRW-HPO", "KRW-DOGE", "KRW-XEC",
        "KRW-SOL", "KRW-MATIC", "KRW-AAVE", "KRW-1INCH", "KRW-FLOW", "KRW-AXS", "KRW-STX",
        "KRW-NEAR", "KRW-ALGO", "KRW-T", "KRW-CELO", "KRW-GMT", "KRW-APT", "KRW-SHIB",
        "KRW-MASK", "KRW-ARB", "KRW-EGLD", "KRW-SUI", "KRW-GRT", "KRW-BLUR", "KRW-IMX",
        "KRW-SEI", "KRW-MINA", "KRW-CTC", "KRW-ASTR", "KRW-ID"
    )

    // 이거 원래 market을 파라미터로 달고, 해당하는 마켓 을 호출해야하는데 흠..
    suspend fun getTradeCenterChartInformation(
        market: String,
        to: String,
        count: Int,
        convertingPriceUnit: String
    ) : NetworkResult<List<TradeCenterDetailChartResponse>?> {

        val response = api.getTradeCenterDetailChartInformation(
            market,
            to,
            count,
            convertingPriceUnit
        )

        return try {

            NetworkResult.Success(response.body())

        } catch (e: Exception) {

            val networkError = networkErrorHandler.errorMessage(e)
            NetworkResult.Error(networkError)

        }

    }

}