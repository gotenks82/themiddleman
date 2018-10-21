package com.eurohack.middleman.models

import java.time.Instant

data class ChatMessage(
        val from: String,
        val content: String,
        val timestamp: Instant = Instant.now()
)