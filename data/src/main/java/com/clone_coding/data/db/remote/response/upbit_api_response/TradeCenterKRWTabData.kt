package com.clone_coding.data.db.remote.response.upbit_api_response

// 이거 지금 아예 필요가 없음.
data class TradeCenterKRWTabMarketListResponse (

    // 마켓 코드 조회 https://docs.upbit.com/v1.4.0/reference/마켓-코드-조회
    val tradeCenterMarketList: List<UpbitMarketInformationResponse>?

)