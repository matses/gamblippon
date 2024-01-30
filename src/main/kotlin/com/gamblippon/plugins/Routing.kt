package com.gamblippon.plugins

import com.gamblippon.infra.primary.rest.playerRoute
import com.gamblippon.infra.primary.rest.pointRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        playerRoute()
        pointRoute()
    }
}
