package com.clone_coding.data.di

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import org.json.JSONException
import org.json.JSONObject
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

class CoinInfoCoinTrendWebSocketListener @Inject constructor() : WebSocketListener() {

    // 마켓 코드 (ex. KRW-BTC)
    private val _code = MutableLiveData<String>()
    val code: LiveData<String>
        get() = _code

    // 누적 매도량
    private val _accAskVolume = MutableLiveData<String>()
    val accAskVolume: LiveData<String>
        get() = _accAskVolume

    // 누적 매수량
    private val _accBidVolume = MutableLiveData<String>()
    val accBidVolume: LiveData<String>
        get() = _accBidVolume

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        super.onMessage(webSocket, bytes)

        Log.d("onMessage2", bytes.toString())
        try {

            val jsonObject = JSONObject(bytes.utf8())
            val parseCode = jsonObject.getString("code")
            val parseAccAskVolume =
                BigDecimal(jsonObject.getDouble("acc_ask_volume")).setScale(0, RoundingMode.DOWN)
                    .toPlainString()
            val parseAccBidVolume =
                BigDecimal(jsonObject.getDouble("acc_bid_volume")).setScale(0, RoundingMode.DOWN)
                    .toPlainString()

            Log.d("coinInfoWebSocket", "code : $parseCode | accAskVolume : $parseAccAskVolume")
            Log.d("coinInfoWebSocket2", "code : $parseCode | accBidVolume : $parseAccBidVolume")

            _code.postValue(parseCode)
            _accAskVolume.postValue(parseAccAskVolume)
            _accBidVolume.postValue(parseAccBidVolume)

        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
    }
}