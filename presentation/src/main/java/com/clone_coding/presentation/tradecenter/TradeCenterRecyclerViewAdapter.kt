package com.clone_coding.presentation.tradecenter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.clone_coding.domain.model.TestModel
import com.clone_coding.presentation.databinding.FragmentTradeListItemBinding


class TradeCenterRecyclerViewAdapter : ListAdapter<TestModel, TradeCenterKRWTabViewHolder>(diffCallback){

    val coinTickersMap = mapOf("KRW-BTC" to "비트코인",
        "KRW-ETH" to "이더리움", "KRW-NEO" to "네오", "KRW-MTL" to "메탈",
        "KRW-XRP" to "리플", "KRW-ETC" to "이더리움클래식", "KRW-SNT" to "스테이터스네트워크토큰",
        "KRW-WAVES" to "웨이브", "KRW-XEM" to "넴", "KRW-QTUM" to "퀀텀",
        "KRW-LSK" to "리스크", "KRW-STEEM" to "스팀", "KRW-XLM" to "스텔라루멘",
        "KRW-ARDR" to "아더", "KRW-ARK" to "아크", "KRW-STORJ" to "스토리지",
        "KRW-GRS" to "그로스톨코인", "KRW-ADA" to "에이다", "KRW-SBD" to "스팀달러",
        "KRW-POWR" to "파워렛저", "KRW-BTG" to "비트코인골드", "KRW-ICX" to "아이콘",
        "KRW-EOS" to "이오스", "KRW-TRX" to "트론", "KRW-SC" to "시아코인",
        "KRW-ONT" to "온톨로지", "KRW-ZIL" to "질리카", "KRW-POLYX" to "폴리매쓰",
        "KRW-ZRX" to "제로엑스", "KRW-LOOM" to "룸네트워크", "KRW-BCH" to "비트코인캐시",
        "KRW-HIFI" to "하이파이", "KRW-ONG" to "온톨로지가스", "KRW-GAS" to "가스",
        "KRW-UPP" to "센티넬프로토콜", "KRW-ELF" to "엘프", "KRW-KNC" to "카이버네트워크",
        "KRW-THETA" to "쎄타토큰", "KRW-QKC" to "쿼크체인", "KRW-BTT" to "비트토렌트",
        "KRW-MOC" to "모스코인", "KRW-TFUEL" to "쎄타퓨엘", "KRW-MANA" to "디센트럴랜드",
        "KRW-ANKR" to "앵커", "KRW-AERGO" to "아르고", "KRW-ATOM" to "코스모스",
        "KRW-TT" to "썬더토큰", "KRW-CRE" to "캐리프로토콜", "KRW-MBL" to "무비블록",
        "KRW-WAXP" to "왁스", "KRW-HBAR" to "헤데라", "KRW-MED" to "메디블록",
        "KRW-STPT" to "에스티피", "KRW-ORBS" to "오브스", "KRW-CHZ" to "칠리즈",
        "KRW-XTZ" to "테조스", "KRW-HIVE" to "하이브", "KRW-KAVA" to "카바",
        "KRW-AHT" to "아하토큰", "KRW-LINK" to "체인링크", "KRW-BORA" to "보라",
        "KRW-JST" to "저스트", "KRW-CRO" to "크로노스", "KRW-SXP" to "스와이프",
        "KRW-HUNT" to "헌트", "KRW-TON" to "톤", "KRW-PLA" to "플레이댑",
        "KRW-DOT" to "폴카닷", "KRW-MVL" to "엠블", "KRW-REI" to "레이",
        "KRW-AQT" to "알파쿼크", "KRW-STRAX" to "스트라티스", "KRW-GLM" to "골렘",
        "KRW-SSX" to "썸씽", "KRW-META" to "메타디움", "KRW-FCT2" to "피르마체인",
        "KRW-CBK" to "코박토큰", "KRW-SAND" to "샌드박스", "KRW-HPO" to "히포크랏",
        "KRW-DOGE" to "도지코인", "KRW-XEC" to "이캐시", "KRW-SOL" to "솔라나",
        "KRW-MATIC" to "폴리곤", "KRW-AAVE" to "에이브", "KRW-1INCH" to "1인치네트워크",
        "KRW-FLOW" to "플로우", "KRW-AXS" to "엑시인피니티", "KRW-STX" to "스택스",
        "KRW-NEAR" to "니어프로토콜", "KRW-ALGO" to "알고랜드", "KRW-T" to "쓰레스홀드",
        "KRW-CELO" to "셀로", "KRW-GMT" to "스테픈", "KRW-APT" to "앱토스",
        "KRW-SHIB" to "시바이누", "KRW-MASK" to "마스크네트워크", "KRW-ARB" to "아비트럼",
        "KRW-EGLD" to "멀티버스엑스", "KRW-SUI" to "수이", "KRW-GRT" to "더그래프",
        "KRW-BLUR" to "블러", "KRW-IMX" to "이뮤터블엑스", "KRW-SEI" to "세이",
        "KRW-MINA" to "미나", "KRW-CTC" to "크레딧코인", "KRW-ASTR" to "아스타",
        "KRW-ID" to "스페이스아이디"
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TradeCenterKRWTabViewHolder {

        return TradeCenterKRWTabViewHolder(FragmentTradeListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false), coinTickersMap)

    }

    override fun onBindViewHolder(holder: TradeCenterKRWTabViewHolder, position: Int) {

        val krwTabData = currentList[position]

        holder.bind(krwTabData)

    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    companion object {

        private val diffCallback = object : DiffUtil.ItemCallback<TestModel>() {

            override fun areItemsTheSame(oldItem: TestModel, newItem: TestModel): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(oldItem: TestModel, newItem: TestModel): Boolean {
                return oldItem == newItem
            }


        }

    }


}

