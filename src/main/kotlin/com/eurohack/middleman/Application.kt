package com.eurohack.middleman

import io.micronaut.runtime.Micronaut
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info

@OpenAPIDefinition(
        info = Info(
                title = "The Middle Man"
        )
)
object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("com.eurohack.middleman")
                .mainClass(Application.javaClass)
                .start()
    }
}