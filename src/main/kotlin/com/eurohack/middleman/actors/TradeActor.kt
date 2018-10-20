package com.eurohack.middleman.actors

import akka.actor.UntypedAbstractActor
import com.eurohack.middleman.models.AskableMessages
import com.eurohack.middleman.models.MessageToUserActor
import com.eurohack.middleman.models.TradeOpportunity

class TradeActor(val tradeId: String) : UntypedAbstractActor() {
    val trade = TradeOpportunity(id = tradeId)
    val manager = context.parent

    override fun onReceive(msg: Any?) {
        when (msg) {
            is TradeOpportunity -> handleTrade(msg)
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
}