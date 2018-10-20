package com.eurohack.middleman.controllers

import com.eurohack.middleman.services.MiddleManService
import io.micronaut.http.HttpResponse.ok
import io.micronaut.http.MediaType
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import javax.inject.Inject

@Controller("/trade")
class TradeController @Inject constructor(
        private val middleManService: MiddleManService
) {

    @Get("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getTrade(id: String) : MutableHttpResponse<Any?> = ok(middleManService.getTrade(id))

}