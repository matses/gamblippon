package com.gamblippon.infra.primary.rest

import com.gamblippon.domain.usecase.PointManagement
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.pointRoute() {

    val pointManagement by inject<PointManagement>()

    route("/point") {
        post {
            val pointRequest = call.receive<PointRequestDto>()
            pointManagement.add(pointRequest.playerId, pointRequest.points)
            call.response.status(HttpStatusCode.Created)
        }
    }
}