package com.gamblippon.infra.primary.rest

import com.gamblippon.domain.model.Ranking
import com.gamblippon.domain.usecase.RankingManagement
import com.gamblippon.infra.primary.rest.RankingMapper.Companion.toDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.rankingRoute() {

    val rankingManagement by inject<RankingManagement>()

    route("/ranking") {
        get {
            val ranking:List<Ranking> = rankingManagement.getRanking()
            call.response.status(HttpStatusCode.OK)
            call.respond(ranking.map { it.toDto() })
        }
    }

    route("/ranking/{playerId}") {
        get {
            val ranking:Ranking = rankingManagement.get(call.parameters["playerId"] !!)
            call.response.status(HttpStatusCode.OK)
            call.respond(ranking.toDto())
        }
    }
}