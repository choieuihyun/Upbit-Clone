package com.clone_coding.data

import android.util.Log
import com.clone_coding.data.db.remote.api.UpbitApi
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.ByteString
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class UpbitMarketCurrentPriceInformationWebsocketTest {

    // Mock 객체 생성
    private lateinit var mockWebServer: MockWebServer
    private val gson = Gson()
    var webSocketList: MutableList<WebSocket> = mutableListOf()

    @Before
    fun setup() {

        mockWebServer = MockWebServer()
        mockWebServer.start()

    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testWebSocketCommunication() {
        // Start the WebSocket server
        val webSocketListener = EchoWebSocketListener()
        mockWebServer.enqueue(MockResponse().withWebSocketUpgrade(webSocketListener))

        // Create WebSocket client
        val client = OkHttpClient()
        val request = Request.Builder().url("wss://api.upbit.com/websocket/v1").build()

        Log.d("requestMessage", request.toString())

        val webSocket = client.newWebSocket(request, webSocketListener)
        webSocketList.add(webSocket)
        webSocket.send(createTicket())
        // Wait for the server's response
        webSocketListener.awaitMessage()

        // Verify the received message
        val receivedMessage = webSocketListener.receivedMessages
        println("Received from server: $receivedMessage")
    }

    private fun createTicket(): String {

        val ticket = Ticket("test")
        val codeList = arrayListOf("KRW-BTC")
        val type = Type("trade", codeList)

        return gson.toJson(arrayListOf(ticket, type))
    }

    data class Ticket(val ticket: String)
    data class Type(val type: String, val codes: List<String>)


    private class EchoWebSocketListener : WebSocketListener() {
        private val receivedMessage = mutableListOf<String>()
        private val messageLatch = java.util.concurrent.CountDownLatch(1)

        override fun onOpen(webSocket: WebSocket, response: Response) {
            println("WebSocket connection opened.")
        }

        override fun onMessage(webSocket: WebSocket, text: ByteString) {
            println("Received from client: $text")
            receivedMessage.add(text.toString())

            // Echo the received message back to the client
            // webSocket.send("Server: $text")
        }

        fun awaitMessage() {
            // Wait for a message to be received
            messageLatch.await(1, TimeUnit.SECONDS)
        }

        val receivedMessages: List<String>
            get() = receivedMessage.toList()
    }

}