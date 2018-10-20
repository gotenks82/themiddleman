package com.eurohack.middleman.actors

import akka.actor.UntypedAbstractActor
import com.eurohack.middleman.models.*

class TradeActor(val tradeId: String) : UntypedAbstractActor() {
    val trade = TradeOpportunity(id = tradeId)
    val manager = context.parent

    override fun onReceive(msg: Any?) {
        when (msg) {
            is TradeOpportunity -> handleTrade(msg)
            is TradeStatusChange -> handleStatusChange(msg)
            AskableMessages.GET_TRADE -> sender.tell(trade, self)
            else -> println("Trade ${trade.id} received message $msg")
        }
    }

    fun handleTrade(tradeOpportunity: TradeOpportunity) {
        trade.copyFrom(tradeOpportunity)
        trade.users.keys.filter { it != trade.rootUserId }.forEach { user ->
            manager.tell(MessageToUserActor(
                    userId = user,
                    message = trade
            ), self)
        }
    }

    fun handleStatusChange(msg: TradeStatusChange) {
        if (!trade.users.keys.contains(msg.userId)) return

        trade.users[msg.userId] = msg.status
        if (trade.isAccepted()) {
            trade.users.keys.forEach { user ->
                manager.tell(MessageToUserActor(
                        userId = user,
                        message = StatusMessages.TRADE_ACCEPTED
                ), self)
            }
        } else if (trade.isRejected()) {
            trade.users.keys.forEach { user ->
                manager.tell(MessageToUserActor(
                        userId = user,
                        message = StatusMessages.TRADE_REJECTED
                ), self)
            }
        }
    }
}