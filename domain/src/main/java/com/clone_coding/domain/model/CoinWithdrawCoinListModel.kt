package com.clone_coding.domain.model

// 이걸 왜 만들었냐? 단순한 이유야. 같은 화면의 같은 리스트에 들어가는 데이터면 같은 데이터겠지 보통
// 모양이 다른것도 아니고 뷰홀더가 여러개가 필요한 것도 아니잖아.
data class CoinWithdrawCoinListModel(

    // 코인 심볼
    val symbol: String,

    // 현재가
    val currentPrice: String,

    // 코인명
    val name: String,

    // 이미지 url
    val imageUrl: String,

    // 총 보유자산
    val coinTotalPrice: String,

    // 코인 보유량
    val coinBalance: String

)