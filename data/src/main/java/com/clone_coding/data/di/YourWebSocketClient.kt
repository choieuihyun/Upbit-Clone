package com.clone_coding.data.di

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Inject

// WebSocket 클라이언트
class YourWebSocketClient @Inject constructor() {
    private var webSocket: WebSocket? = null

    fun connectWebSocket(request: Request, listener: WebSocketListener): WebSocket {
        val okHttpClient = OkHttpClient.Builder().build()
        webSocket = okHttpClient.newWebSocket(request, listener)
        return webSocket!!
    }

    fun sendWebSocketMessage(message: String) {
        webSocket?.send(message)
    }
}