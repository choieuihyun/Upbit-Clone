package com.clone_coding.data.di

import android.util.Log
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import okhttp3.Interceptor
import okhttp3.Response
import java.util.UUID

class JwtInterceptor(private val accessKey: String, private val secretKey: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val algorithm = Algorithm.HMAC256(secretKey)
        val jwtToken = JWT.create()
            .withClaim("access_key", accessKey)
            .withClaim("nonce", UUID.randomUUID().toString())
            .sign(algorithm)

        val authenticationToken = "Bearer $jwtToken"

        val original = chain.request()
        val requestBuilder = original.newBuilder()
            .header("Content-Type", "application/json")
            .addHeader("Authorization", authenticationToken)
            .method(original.method, original.body)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}