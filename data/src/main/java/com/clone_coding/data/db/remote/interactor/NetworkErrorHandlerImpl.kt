package com.clone_coding.data.db.remote.interactor

import com.clone_coding.domain.error.NetworkError
import com.clone_coding.domain.error.NetworkErrorHandler
import retrofit2.HttpException
import java.net.SocketTimeoutException

class NetworkErrorHandlerImpl: NetworkErrorHandler {

    override fun errorMessage(exception: Exception): NetworkError {

        return when (exception) {

            is SocketTimeoutException -> NetworkError.Timeout

            is HttpException -> { // Http 관련 Exception 이면 해당 에러 코드에 따른 메세지 처리
                when (exception.code()) {
                    in 500..599 -> NetworkError.InternalServer
                    in 400..499 -> {
                        val code = exception.code()
                        NetworkError.BadRequest(code)
                    }
                    else -> NetworkError.Unknown // 아니면 모르는 에러로 처리
                }
            }
            else -> NetworkError.Unknown // 아니면 모르는 에러로 처리
        }

    }


}