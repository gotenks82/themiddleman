package com.eurohack.middleman.actors

import akka.actor.UntypedAbstractActor
import com.eurohack.middleman.models.User

class UserActor(val userId: String) : UntypedAbstractActor() {
    val user = User(userId)

    override fun onReceive(p0: Any?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}