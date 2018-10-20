package com.eurohack.middleman.models

import java.math.BigDecimal

data class TradeSummary(
        val id : String,
        val nSteps: Int,
        val userStatus: TradeOpportunityStatus,
        val tradeStatus: TradeOpportunityStatus,
        val priceDelta: BigDecimal,
        val nMessages: Int,
        val buying: Interest,
        val selling: Interest
)