package com.eurohack.middleman.models

data class MessageToUserActor(val userId: String, val message: Any)
data class MessageToTradeActor(val tradeId: String, val message: Any)