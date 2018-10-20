package com.eurohack.middleman.services

import akka.actor.ActorSystem
import akka.actor.Props
import akka.pattern.PatternsCS.ask
import akka.util.Timeout
import com.eurohack.middleman.actors.MiddleManActor
import com.eurohack.middleman.models.*
import com.eurohack.middleman.models.AskableMessages.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Singleton
class MiddleManService {

    val askTimeout = Timeout.apply(3, TimeUnit.SECONDS)

    val actorSystem = ActorSystem.create("middleman")
    val middleManManager = actorSystem.actorOf(Props.create(MiddleManActor::class.java))

    fun addInterest(userId: String, interest: Interest) =
            middleManManager.tell(MessageToUserActor(userId, interest), null)

    fun getNotifications(userId: String): List<String> =
            ask(middleManManager, MessageToUserActor(userId, GET_NOTIFICATIONS), askTimeout).toCompletableFuture().get() as List<String>

    private fun getTradeFuture(tradeId: String): CompletableFuture<Any?> =
            ask(middleManManager, MessageToTradeActor(tradeId, GET_TRADE), askTimeout).toCompletableFuture()

    fun getTrade(tradeId: String): TradeOpportunity = getTradeFuture(tradeId).get() as TradeOpportunity

    fun getTradesByUser(userId: String): List<TradeSummary> {
        val trades = ask(middleManManager, MessageToUserActor(
                userId = userId,
                message = GET_TRADES
        ), askTimeout).toCompletableFuture().toCompletableFuture().get() as List<String>

        val futures = trades.map {
            getTradeFuture(it)
        }

        return futures.map { (it.get() as TradeOpportunity).toSummary(userId) }
    }
}