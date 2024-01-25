package com.gamblippon.plugins

import com.gamblippon.infra.primary.rest.playerRoute
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        playerRoute()
    }
}
