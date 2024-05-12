package com.clone_coding.data.di

import com.google.gson.Gson

class WebSocketTicket {

    companion object {

        data class Ticket(val ticket: String)
        data class Type(val type: String, val codes: List<String>)

        private val gson = Gson()

        fun createTicket(): String {
            val ticket = Ticket("tickerTicket")
            val codeList = arrayListOf(
                "KRW-BTC", "KRW-ETH", "KRW-NEO", "KRW-MTL", "KRW-XRP",
                "KRW-ETC", "KRW-SNT", "KRW-WAVES", "KRW-XEM", "KRW-QTUM", "KRW-LSK", "KRW-STEEM",
                "KRW-XLM", "KRW-ARDR", "KRW-ARK", "KRW-STORJ", "KRW-GRS", "KRW-ADA", "KRW-SBD",
                "KRW-POWR", "KRW-BTG", "KRW-ICX", "KRW-EOS", "KRW-TRX", "KRW-SC", "KRW-ONT",
                "KRW-ZIL", "KRW-POLYX", "KRW-ZRX", "KRW-LOOM", "KRW-BCH", "KRW-HIFI", "KRW-ONG",
                "KRW-GAS", "KRW-UPP", "KRW-ELF", "KRW-KNC", "KRW-THETA", "KRW-QKC", "KRW-BTT",
                "KRW-MOC", "KRW-TFUEL", "KRW-MANA", "KRW-ANKR", "KRW-AERGO", "KRW-ATOM", "KRW-TT",
                "KRW-CRE", "KRW-MBL", "KRW-WAXP", "KRW-HBAR", "KRW-MED", "KRW-STPT", "KRW-ORBS",
                "KRW-CHZ", "KRW-XTZ", "KRW-HIVE", "KRW-KAVA", "KRW-AHT", "KRW-LINK", "KRW-BORA",
                "KRW-JST", "KRW-CRO", "KRW-SXP", "KRW-HUNT", "KRW-TON", "KRW-PLA", "KRW-DOT",
                "KRW-MVL", "KRW-REI", "KRW-AQT", "KRW-STRAX", "KRW-GLM", "KRW-SSX", "KRW-META",
                "KRW-FCT2", "KRW-CBK", "KRW-SAND", "KRW-HPO", "KRW-DOGE", "KRW-XEC", "KRW-SOL",
                "KRW-MATIC", "KRW-AAVE", "KRW-1INCH", "KRW-FLOW", "KRW-AXS", "KRW-STX", "KRW-NEAR",
                "KRW-ALGO", "KRW-T", "KRW-CELO", "KRW-GMT", "KRW-APT", "KRW-SHIB", "KRW-MASK",
                "KRW-ARB", "KRW-EGLD", "KRW-SUI", "KRW-GRT", "KRW-BLUR", "KRW-IMX", "KRW-SEI",
                "KRW-MINA", "KRW-CTC", "KRW-ASTR", "KRW-ID"
            )
            val type = Type("ticker", codeList)

            return gson.toJson(arrayListOf(ticket, type))
        }

    }
}