package com.clone_coding.data.datasource.remote_datasource

import android.util.Log
import com.clone_coding.data.db.remote.api.CoinGeckoApi
import com.clone_coding.data.db.remote.api.UpbitJwtApi
import com.clone_coding.data.db.remote.response.coin_investment_asset_hold_response.CoinInvestmentAssetHoldResponse
import com.clone_coding.data.db.remote.response.coingecko_api_response.CoinAllDataResponse
import com.clone_coding.domain.error.NetworkErrorHandler
import com.clone_coding.domain.error.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.Exception

class CoinWithdrawDatasource @Inject constructor(

    private val upbitJwtApi: UpbitJwtApi,
    private val coinGeckoApi: CoinGeckoApi,
    private val networkErrorHandler: NetworkErrorHandler

) {

    private val krwMarketCoinNameList = listOf(
        "bitcoin",
        "ethereum",
        "neo",
        "metal",
        "ripple",
        "ethereum-classic",
        "status",
        "waves",
        "nem",
        "qtum",
        "lisk",
        "steem",
        "stellar",
        "ardor",
        "ark",
        "storj",
        "groestlcoin",
        "cardano",
        "steem-dollars",
        "power-ledger",
        "bitcoin-gold",
        "icon",
        "eos",
        "tron",
        "siacoin",
        "ontology",
        "zilliqa",
        "polymath",
        "0x",
        "loom-network",
        "bitcoin-cash",
        "hyprr",
        "ontology-gas",
        "gas",
        "sentinel-protocol",
        "aelf",
        "kyber-network",
        "theta-token",
        "quarkchain",
        "bittorrent",
        "mossland",
        "theta-fuel",
        "decentraland",
        "ankr",
        "aergo",
        "cosmos",
        "trust-token",
        "carry",
        "moviebloc",
        "wax",
        "hedera-hashgraph",
        "medibloc",
        "standard-tokenization-protocol",
        "orbs",
        "chiliz",
        "tezos",
        "hive",
        "kava",
        "a-hit",
        "chainlink",
        "bora",
        "just",
        "crypto-com",
        "swipe",
        "hunt-token",
        "tokamak-network",
        "playchip",
        "polkadot",
        "mass-vehicle-ledger",
        "alpha-quadrant",
        "stratis",
        "golem",
        "shookstake",
        "metadium",
        "firma-chain",
        "covid-blood-kills",
        "the-sandbox",
        "hyper-pay",
        "dogecoin",
        "eternal-cash",
        "solana",
        "polygon",
        "aave",
        "1inch",
        "flow",
        "axie-infinity",
        "stacks",
        "near-protocol",
        "algorand",
        "tomochain",
        "celo",
        "global-markets-token",
        "alpha-platform",
        "shiba-inu",
        "mask-network",
        "arbitrum",
        "elrond",
        "swissborg",
        "the-graph",
        "blur-network",
        "immutable-x",
        "seigniorage-shares",
        "mina-protocol",
        "culture-ticket-chain",
        "astral-protocol",
        "everest"
    )


    suspend fun getCoinList(): NetworkResult<MutableList<CoinAllDataResponse>> {

        val responseList = mutableListOf<CoinAllDataResponse>()

        val response =

            coinGeckoApi.getCoinAllDataList(
                "krw",
                krwMarketCoinNameList.joinToString(","),
                "market_cap_desc",
                "100",
                "1",
                "7d",
                "ko"
            )

        response.body()?.let { body ->

            for (data in body) {
                responseList.add(data)
            }

        }

        Log.d("withdrawDatasource1", responseList.toString())

        return try {


            NetworkResult.Success(responseList)

        } catch (e: Exception) {

            val networkError = networkErrorHandler.errorMessage(e)
            NetworkResult.Error(networkError)

        }

    }

    suspend fun getHoldAsset(): NetworkResult<List<CoinInvestmentAssetHoldResponse>?> {

        val response = upbitJwtApi.getInvestmentAssetHold()

        return try {

            Log.d("withdrawDatasource2", response.toString())
            NetworkResult.Success(response.body())

        } catch (e: Exception) {

            val networkError = networkErrorHandler.errorMessage(e)
            NetworkResult.Error(networkError)

        }

    }

}