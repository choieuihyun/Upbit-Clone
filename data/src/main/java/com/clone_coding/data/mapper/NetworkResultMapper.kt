package com.clone_coding.data.mapper

import com.clone_coding.domain.error.NetworkErrorHandler
import com.clone_coding.domain.error.NetworkResult

suspend fun <T, R> NetworkResult<T>.mapNetworkResult(getData: suspend (T) -> R): NetworkResult<R> {
    return changeNetworkData(getData(toNetworkResult()))
}

private fun <R> changeNetworkData(replaceData: R): NetworkResult<R> {
    return NetworkResult.Success(replaceData)
}

fun <T> NetworkResult<T>.toNetworkResult(): T {

    return (this as NetworkResult.Success).data

}


//fun <T> NetworkResult<T>.toNetworkResult(networkErrorHandler: NetworkErrorHandler): T {
//
//    return when(this) {
//
//        is NetworkResult.Success -> {
//
//            this.data
//
//        }
//
//        is NetworkResult.Error -> {
//
//            val networkError = networkErrorHandler.errorMessage(this.errorType)
//            NetworkResult.Error(networkError) as T
//
//        }
//
//    }
//
//
//}


