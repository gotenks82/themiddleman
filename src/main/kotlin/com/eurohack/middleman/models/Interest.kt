package com.eurohack.middleman.models

import java.math.BigDecimal

data class Interest(
        val itemUserid: String,
        val itemId: String,
        val itemName: String = "",
        val itemImageUrl: String = "",
        val itemUrl: String = "",
        val itemPrice: BigDecimal = 10.toBigDecimal(),
        val platform: String = ""
)