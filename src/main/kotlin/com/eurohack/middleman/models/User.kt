package com.eurohack.middleman.models

class User(
        val id: String,
        val config: UserConfiguration = UserConfiguration(),
        val interests: List<Interest> = mutableListOf(),
        val tradeOpportunities: Map<String, TradeOpportunityStatus> = mutableMapOf(),
        val notifications: List<String> = mutableListOf()
) {
}