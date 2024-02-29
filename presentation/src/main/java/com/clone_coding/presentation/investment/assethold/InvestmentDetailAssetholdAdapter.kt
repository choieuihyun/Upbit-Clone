package com.clone_coding.presentation.investment.assethold

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.clone_coding.domain.model.CoinInvestmentAssetHoldModel
import com.clone_coding.presentation.databinding.FragmentInvestmentDetailAssetheldViewholderBinding

class InvestmentDetailAssetholdAdapter: ListAdapter<CoinInvestmentAssetHoldModel, InvestmentDetailAssetholdViewHolder> (
    diffCallback
){

    val coinTickersMap = mapOf("BTC" to "비트코인",
        "ETH" to "이더리움", "NEO" to "네오", "MTL" to "메탈",
        "XRP" to "리플", "ETC" to "이더리움클래식", "SNT" to "스테이터스네트워크토큰",
        "WAVES" to "웨이브", "XEM" to "넴", "QTUM" to "퀀텀",
        "LSK" to "리스크", "STEEM" to "스팀", "XLM" to "스텔라루멘",
        "ARDR" to "아더", "ARK" to "아크", "STORJ" to "스토리지",
        "GRS" to "그로스톨코인", "ADA" to "에이다", "SBD" to "스팀달러",
        "POWR" to "파워렛저", "BTG" to "비트코인골드", "ICX" to "아이콘",
        "EOS" to "이오스", "TRX" to "트론", "SC" to "시아코인",
        "ONT" to "온톨로지", "ZIL" to "질리카", "POLYX" to "폴리매쓰",
        "ZRX" to "제로엑스", "LOOM" to "룸네트워크", "BCH" to "비트코인캐시",
        "HIFI" to "하이파이", "ONG" to "온톨로지가스", "GAS" to "가스",
        "UPP" to "센티넬프로토콜", "ELF" to "엘프", "KNC" to "카이버네트워크",
        "THETA" to "쎄타토큰", "QKC" to "쿼크체인", "BTT" to "비트토렌트",
        "MOC" to "모스코인", "TFUEL" to "쎄타퓨엘", "MANA" to "디센트럴랜드",
        "ANKR" to "앵커", "AERGO" to "아르고", "ATOM" to "코스모스",
        "TT" to "썬더토큰", "CRE" to "캐리프로토콜", "MBL" to "무비블록",
        "WAXP" to "왁스", "HBAR" to "헤데라", "MED" to "메디블록",
        "STPT" to "에스티피", "ORBS" to "오브스", "CHZ" to "칠리즈",
        "XTZ" to "테조스", "HIVE" to "하이브", "KAVA" to "카바",
        "AHT" to "아하토큰", "LINK" to "체인링크", "BORA" to "보라",
        "JST" to "저스트", "CRO" to "크로노스", "SXP" to "스와이프",
        "HUNT" to "헌트", "TON" to "토카막네트워크", "PDA" to "플레이댑",
        "DOT" to "폴카닷", "MVL" to "엠블", "REI" to "레이",
        "AQT" to "알파쿼크", "STRAX" to "스트라티스", "GLM" to "골렘",
        "SSX" to "썸씽", "META" to "메타디움", "FCT2" to "피르마체인",
        "CBK" to "코박토큰", "SAND" to "샌드박스", "HPO" to "히포크랏",
        "DOGE" to "도지코인", "XEC" to "이캐시", "SOL" to "솔라나",
        "MATIC" to "폴리곤", "AAVE" to "에이브", "1INCH" to "1인치네트워크",
        "FLOW" to "플로우", "AXS" to "엑시인피니티", "STX" to "스택스",
        "NEAR" to "니어프로토콜", "ALGO" to "알고랜드", "T" to "쓰레스홀드",
        "CELO" to "셀로", "GMT" to "스테픈", "APT" to "앱토스",
        "SHIB" to "시바이누", "MASK" to "마스크네트워크", "ARB" to "아비트럼",
        "EGLD" to "멀티버스엑스", "SUI" to "수이", "GRT" to "더그래프",
        "BLUR" to "블러", "IMX" to "이뮤터블엑스", "SEI" to "세이",
        "MINA" to "미나", "CTC" to "크레딧코인", "ASTR" to "아스타",
        "ID" to "스페이스아이디", "PUNDIX" to "펀디엑스")

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InvestmentDetailAssetholdViewHolder {

        return InvestmentDetailAssetholdViewHolder(
            FragmentInvestmentDetailAssetheldViewholderBinding.inflate(LayoutInflater.from(parent.context), parent, false), coinTickersMap)

    }

    override fun onBindViewHolder(holder: InvestmentDetailAssetholdViewHolder, position: Int) {

        val investmentAssetHold = currentList[position]
        holder.bind(investmentAssetHold)

    }

    companion object {

        private val diffCallback =
            object : DiffUtil.ItemCallback<CoinInvestmentAssetHoldModel>() {

                override fun areItemsTheSame(
                    oldItem: CoinInvestmentAssetHoldModel,
                    newItem: CoinInvestmentAssetHoldModel
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }

                override fun areContentsTheSame(
                    oldItem: CoinInvestmentAssetHoldModel,
                    newItem: CoinInvestmentAssetHoldModel
                ): Boolean {
                    return oldItem == newItem
                }


            }

    }

}