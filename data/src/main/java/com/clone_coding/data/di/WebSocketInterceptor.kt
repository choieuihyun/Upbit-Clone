package com.clone_coding.data.di

import okhttp3.Interceptor
import okhttp3.Response
import java.util.Base64
import java.util.Random
import javax.inject.Inject

class WebSocketInterceptor @Inject constructor(
    private val webSocketClient: WebSocketClient,
    private val webSocketListener: TradeCenterWebSocketListener,
    private val webSocketUrl: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        if (shouldUpgradeToWebSocket()) {
            val secWebSocketKey = generateSecWebSocketKey()

            webSocketClient.connectWebSocket(originalRequest, webSocketListener)

            val updatedRequest = originalRequest.newBuilder()
                .header("Upgrade", "websocket")
                .header("Connection", "Upgrade")
                .header("Sec-WebSocket-Key", secWebSocketKey)
                .header("Sec-WebSocket-Version", "13")
                .url(webSocketUrl)
                .build()

            return chain.proceed(updatedRequest)
        }

        return chain.proceed(originalRequest)
    }

    private fun shouldUpgradeToWebSocket(): Boolean {
        // 특정 엔드포인트에 대한 요청일 경우 웹소켓으로 업그레이드할 수 있는 조건을 정의
        return true
    }

    private fun generateSecWebSocketKey(): String {
        val random = Random()
        val byteArray = ByteArray(16)
        random.nextBytes(byteArray)
        return Base64.getEncoder().encodeToString(byteArray)
    }
}