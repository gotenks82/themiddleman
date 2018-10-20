package com.eurohack.middleman.models

enum class AskableMessages {
    GET_NOTIFICATIONS,
    GET_TRADES,
    GET_MESSAGES,
    GET_TRADE
}

data class MessageToUserActor(val userId: String, val message: Any)
data class MessageToTradeActor(val tradeId: String, val message: Any)