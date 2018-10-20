package com.eurohack.middleman.models

import java.time.ZonedDateTime

data class Message(val from: String, val content: String, val timestamp: ZonedDateTime = ZonedDateTime.now())