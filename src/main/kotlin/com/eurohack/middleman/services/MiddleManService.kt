package com.eurohack.middleman.services

import akka.actor.ActorSystem
import akka.actor.Props
import akka.pattern.PatternsCS.ask
import akka.util.Timeout
import com.eurohack.middleman.actors.MiddleManActor
import com.eurohack.middleman.models.AskableMessages.*
import com.eurohack.middleman.models.Interest
import com.eurohack.middleman.models.MessageToUserActor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Singleton
class MiddleManService {

    val askTimeout = Timeout.apply(3, TimeUnit.SECONDS)

    val actorSystem = ActorSystem.create("middleman")
    val middleManManager = actorSystem.actorOf(Props.create(MiddleManActor::class.java))

    fun addInterest(userId: String, interest: Interest) {
        middleManManager.tell(MessageToUserActor(userId, interest), null)
    }

    fun getNotifications(userId: String): List<String> {
        return ask(middleManManager, MessageToUserActor(userId, GET_NOTIFICATIONS), askTimeout).toCompletableFuture().get() as List<String>
    }
}