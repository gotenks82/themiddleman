package com.eurohack.middleman.actors

import akka.actor.UntypedAbstractActor
import com.eurohack.middleman.models.*

class UserActor(val userId: String) : UntypedAbstractActor() {
    val user = User(userId)
    val manager = context.parent

    override fun onReceive(msg: Any?) {
        when (msg) {
            is Interest -> addInterest(msg)
            is TradeOpportunity -> handleTrade(msg)
            is TradeStatusChange -> handleStatusChange(msg)
            AskableMessages.GET_NOTIFICATIONS -> sender.tell(getNotifications(), self)
            AskableMessages.GET_TRADES -> sender.tell(user.tradeOpportunities.keys.toList(), self)
            StatusMessages.TRADE_ACCEPTED -> user.notifications.add("Your Trading opportunity was ACCEPTED!")
            StatusMessages.TRADE_REJECTED -> user.notifications.add("Your Trading opportunity was REJECTED!")
            else -> println("Unknown message ${msg}")
        }
    }

    fun addInterest(interest: Interest) {
        if (user.interests.contains(interest)) return

        user.interests.add(interest)
        TradeOpportunity().apply {
            addStep(TradeStep(user.id, interest))
        }.let {
            println("User ${user.id} forwards trade ${it.id} to user ${interest.itemUserid}")
            manager.tell(MessageToUserActor(
                    userId = interest.itemUserid,
                    message = it
            ), self)
        }
    }

    fun handleTrade(trade: TradeOpportunity) {
        if (trade.isComplete()) {
            saveTrade(trade)
        } else if (!trade.isUserInvolved(user.id)) {
            forwardTrade(trade)
        }
    }

    fun forwardTrade(trade: TradeOpportunity) {
        user.interests.filter {
            !trade.wasReceivedBy(it.itemUserid)
        }.forEach { interest ->
            TradeOpportunity().apply {
                copyFrom(trade)
                addStep(TradeStep(user.id, interest))
            }.let {
                println("User ${user.id} forwards trade ${trade.id} to user ${interest.itemUserid}")
                manager.tell(MessageToUserActor(
                        userId = interest.itemUserid,
                        message = it
                ), self)
            }
        }
    }

    fun saveTrade(trade: TradeOpportunity) {
        if (user.tradeOpportunities.containsKey(trade.id)) return

        if (trade.rootUserId == userId) {
            manager.tell(MessageToTradeActor(
                    tradeId = trade.id,
                    message = trade
            ), self)
        }

        user.tradeOpportunities.put(trade.id, TradeOpportunityStatus.PENDING)
        user.notifications.add("You have a new Trading opportunity!")
        println("${user.id} has a new Trading opportunity!")
    }

    fun getNotifications(): List<String> {
        val notifications = mutableListOf<String>().apply { addAll(user.notifications) }
        user.notifications.clear()
        return notifications
    }

    fun handleStatusChange(msg: TradeStatusChange) {
        if (!user.tradeOpportunities.keys.contains(msg.tradeId)) return

        user.tradeOpportunities[msg.tradeId] = msg.status
        manager.tell(MessageToTradeActor(
                tradeId = msg.tradeId,
                message = msg
        ), self)
    }
}