package com.clone_coding.domain.error

interface NetworkErrorHandler {

    fun errorMessage(exception: Exception): NetworkError

}