package com.clone_coding.data

import com.clone_coding.data.db.remote.api.UpbitApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UpbitMarketCurrentPriceInformationTest {

    // Mock 객체 생성
    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: UpbitApi

    @Before
    fun setup() {

        mockWebServer = MockWebServer()
        mockWebServer.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("https://api.upbit.com/v1/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(UpbitApi::class.java)

    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testApiCallSuccess() = runBlocking {

        val response = MockResponse().setResponseCode(200).setBody("{ \"message\": \"success\" }")

        mockWebServer.enqueue(response)

        val actualResponse = api.getCurrentPriceInformation("KRW-BTC")

        Assert.assertTrue(actualResponse.isSuccessful)
        // assertEquals("{ message\": \"success\" }", actualResponse.body())

        println(actualResponse.body().toString())


    }
}