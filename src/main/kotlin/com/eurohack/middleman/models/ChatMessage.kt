package com.eurohack.middleman.models

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Instant

data class ChatMessage(
        val from: String,
        val content: String,
        @JsonFormat()
        val timestamp: Instant = Instant.now()
)