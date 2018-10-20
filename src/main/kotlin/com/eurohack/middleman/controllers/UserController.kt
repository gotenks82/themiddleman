package com.eurohack.middleman.controllers

import com.eurohack.middleman.models.Interest
import com.eurohack.middleman.models.TradeOpportunityStatus
import com.eurohack.middleman.services.MiddleManService
import io.micronaut.http.HttpResponse.accepted
import io.micronaut.http.HttpResponse.ok
import io.micronaut.http.MediaType
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.*
import javax.inject.Inject

@Controller("/user")
class UserController @Inject constructor(
        private val middleManService: MiddleManService
) {

    @Post("/{id}/interest")
    fun addInterest(id: String, @Body interest: Interest) : MutableHttpResponse<Any?> {
        middleManService.addInterest(id, interest)
        return accepted()
    }

    @Get("/{id}/notifications")
    @Produces(MediaType.APPLICATION_JSON)
    fun getNotifications(id: String) : MutableHttpResponse<Any?> = ok(middleManService.getNotifications(id))

    @Get("/{id}/trades")
    @Produces(MediaType.APPLICATION_JSON)
    fun getTrades(id: String) : MutableHttpResponse<Any?> = ok(middleManService.getTradesByUser(id))

    @Post("/{id}/trades/{tradeId}/status/{status}")
    fun updateStatus(id: String, tradeId: String, status: TradeOpportunityStatus) = ok(middleManService.updateStatus(id, tradeId, status))

}