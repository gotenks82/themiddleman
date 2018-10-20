package com.eurohack.middleman.actors

import akka.actor.UntypedAbstractActor
import com.eurohack.middleman.models.TradeOpportunity

class TradeActor(val tradeId: String) : UntypedAbstractActor() {
    val trade = TradeOpportunity(tradeId)

    override fun onReceive(msg: Any?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}