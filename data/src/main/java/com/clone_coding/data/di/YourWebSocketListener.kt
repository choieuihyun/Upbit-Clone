package com.clone_coding.data.di

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonParser
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

// WebSocket 리스너
class YourWebSocketListener @Inject constructor() : WebSocketListener() {

    // 현재가(Ticker) 데이터

    // 마켓 코드 (ex. KRW-BTC)
    private val _code = MutableLiveData<String>()
    val code: LiveData<String> = _code

    // 현재가
    private val _tradePrice = MutableLiveData<String>()
    val tradePrice: LiveData<String> = _tradePrice

    // 전일 대비 등락율
    private val _signedChangeRate = MutableLiveData<String>()
    val signedChangeRate: LiveData<String> = _signedChangeRate

    // 24시간 누적 거래대금
    private val _accTradePrice24H = MutableLiveData<String>()
    val accTradePrice24H: LiveData<String> = _accTradePrice24H

    override fun onOpen(webSocket: WebSocket, response: Response) {
        // 웹소켓이 열릴 때의 동작
        Log.d("openMessage", "WebSocket connection opened successfully.")
        //_webSocketData.postValue(response.toString())
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        // 웹소켓에서 메시지를 받았을 때의 동작
        Log.d("onMessage", bytes.toString())

        try {
                val jsonObject = JSONObject(bytes.utf8().toString())
                val parseCode = jsonObject.getString("code")
                val parseTradePrice = BigDecimal(jsonObject.getDouble("trade_price")).toPlainString()
                val parseSignedChangeRate = BigDecimal(jsonObject.getDouble("signed_change_rate") * 100).setScale(3, RoundingMode.DOWN).toPlainString()
                val parseAccTradePrice24H = BigDecimal(jsonObject.getDouble("acc_trade_price_24h") / 1000000).setScale(0,RoundingMode.DOWN).toPlainString()
                // 각 메시지에 대한 처리 로직 수행
                Log.d("codeAndTradePrice", "code : $code | tradePrice : $tradePrice")
                Log.d("codeAndTradePrice2",  "signedChangeRate : $signedChangeRate% | accTradePrice24H : $accTradePrice24H")

                _code.postValue(parseCode)
                _tradePrice.postValue(parseTradePrice)
                _signedChangeRate.postValue(parseSignedChangeRate)
                _accTradePrice24H.postValue(parseAccTradePrice24H)

        } catch (e: JSONException) {
            e.printStackTrace()
        }


    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        // 웹소켓이 닫혔을 때의 동작
        Log.e("onCloseMessage", "WebSocket connection failure. Error: ")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        // 웹소켓 연결이 실패했을 때의 동작
        Log.e("onFailureMessage", "WebSocket connection failure. Error: ${t.message}")
    }

}
