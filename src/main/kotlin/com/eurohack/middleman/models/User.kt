package com.eurohack.middleman.models

class User(
        val id: String,
        val config: UserConfiguration = UserConfiguration(),
        val interests: MutableList<Interest> = mutableListOf<Interest>(),
        val tradeOpportunities: MutableMap<String, TradeOpportunityStatus> = mutableMapOf()
) {
    var notifications: MutableList<String> = mutableListOf()
}