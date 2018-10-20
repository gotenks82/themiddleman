package com.eurohack.middleman.models

import java.math.BigDecimal

data class Interest(
        val itemUsername: String,
        val itemId: String,
        val itemName: String,
        val itemImageUrl: String,
        val itemUrl: String,
        val itemPrice: BigDecimal,
        val platform: String
)