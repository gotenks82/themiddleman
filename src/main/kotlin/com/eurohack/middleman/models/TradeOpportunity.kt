package com.eurohack.middleman.models

import com.eurohack.middleman.models.TradeOpportunityStatus.*
import com.fasterxml.jackson.annotation.JsonInclude
import java.util.*

class TradeOpportunity(
        val id: String = UUID.randomUUID().toString(),
        val steps: MutableList<TradeStep> = mutableListOf(),
        val users: MutableMap<String, TradeOpportunityStatus> = mutableMapOf(),
        val messages: MutableList<Message> = mutableListOf()
) {
    var rootUserId: String = ""
    var maxUsers = 10

    fun isLimitExceeded(): Boolean = maxUsers < steps.size

    @JsonInclude
    fun getStatus(): TradeOpportunityStatus = when {
        users.any { it.value == REJECTED } -> REJECTED
        users.all { it.value == ACCEPTED } -> ACCEPTED
        users.any { it.value == DONE } -> DONE
        else -> PENDING
    }

    fun isAccepted(): Boolean = getStatus() == ACCEPTED
    fun isRejected(): Boolean = getStatus() == REJECTED

    fun isUserInvolved(user: String) = steps.any { it.userId == user }

    fun wasReceivedBy(user: String) = steps.any { it.interest.itemUserid == user }

    fun isComplete() = steps.any { it.interest.itemUserid == rootUserId }

    fun addStep(step: TradeStep) {
        if (rootUserId.isBlank()) {
            rootUserId = step.userId
        }
        steps.add(step)
        users[step.userId] = PENDING
    }

    fun copyFrom(tradeOpportunity: TradeOpportunity) {
        rootUserId = tradeOpportunity.rootUserId
        steps.apply {
            clear()
            addAll(tradeOpportunity.steps)
        }
        maxUsers = tradeOpportunity.maxUsers
        users.apply {
            clear()
            putAll(tradeOpportunity.users)
        }
        messages.apply {
            clear()
            addAll(tradeOpportunity.messages)
        }
    }

    fun toSummary(userId: String) : TradeSummary {
        val selling = requireNotNull(steps.find { it.interest.itemUserid == userId })
        val buying = requireNotNull(steps.find { it.userId == userId })

        return TradeSummary(
                id= id,
                nSteps = steps.count(),
                userStatus = users[userId] ?: PENDING,
                tradeStatus = getStatus(),
                nMessages = messages.count(),
                priceDelta = selling.interest.itemPrice - buying.interest.itemPrice,
                selling = selling.interest,
                buying = buying.interest
        )
    }
}

enum class TradeOpportunityStatus {
    PENDING,
    REJECTED,
    ACCEPTED,
    DONE
}