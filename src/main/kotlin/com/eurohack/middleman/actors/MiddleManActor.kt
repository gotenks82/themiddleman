package com.eurohack.middleman.actors

import akka.actor.ActorRef
import akka.actor.Props
import akka.actor.UntypedAbstractActor
import com.eurohack.middleman.models.MessageToTradeActor
import com.eurohack.middleman.models.MessageToUserActor

class MiddleManActor : UntypedAbstractActor() {

    val users = mutableMapOf<String, ActorRef>()
    val trades = mutableMapOf<String, ActorRef>()

    override fun onReceive(msg: Any?) {
        when(msg) {
            is MessageToUserActor -> getUserFor(msg.userId).tell(msg.message, sender())
            is MessageToTradeActor -> getTradeFor(msg.tradeId).tell(msg.message, sender())
            else -> println("Unknown message ${msg}")
        }
    }

    fun getUserFor(id: String) : ActorRef {
        if (!users.containsKey(id)) {
            users[id] = context.actorOf(Props.create(UserActor::class.java, id), id)
        }
        return requireNotNull(users[id])
    }

    fun getTradeFor(id: String) : ActorRef {
        if (!trades.containsKey(id)) {
            trades[id] = context.actorOf(Props.create(TradeActor::class.java, id), id)
        }
        return requireNotNull(trades[id])
    }
}