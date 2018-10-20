package com.eurohack.middleman.models

data class TradeStatusChange(val userId: String, val tradeId: String, val status: TradeOpportunityStatus)