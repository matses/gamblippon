package com.gamblippon.infra.primary.rest

import com.gamblippon.domain.model.Player
import com.gamblippon.domain.usecase.PlayerManagement
import com.gamblippon.infra.primary.rest.PlayerMapper.Companion.toDomain
import com.gamblippon.infra.primary.rest.PlayerMapper.Companion.toDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.playerRoute() {

    val playerManagement by inject<PlayerManagement>()

    route("/player") {
        post {
            val player = call.receive<PlayerDto>()
            val registeredPlayer:Player = playerManagement.add(player.toDomain())
            call.response.status(HttpStatusCode.Created)
            call.response.header("Location", "/player/"+registeredPlayer.id)
            call.respond(registeredPlayer.toDto())
        }
    }
}