package com.eurohack.middleman.models

import com.eurohack.middleman.models.TradeOpportunityStatus.*

class TradeOpportunity(
        val id: String
) {

    val steps: List<TradeStep> = mutableListOf()
    val users: Map<String, TradeOpportunityStatus> = mutableMapOf()
    val messages: List<Message> = mutableListOf()
    var maxUsers = 10

    fun isLimitExceeded(): Boolean = maxUsers < steps.size

    fun getStatus(): TradeOpportunityStatus = when {
        users.any { it.value == REJECTED } -> REJECTED
        users.all { it.value == ACCEPTED } -> ACCEPTED
        users.any { it.value == DONE } -> DONE
        else -> PENDING
    }

    fun isUserInvolved(user: String) = steps.any { it.userId == user }
}

enum class TradeOpportunityStatus {
    PENDING,
    REJECTED,
    ACCEPTED,
    DONE
}