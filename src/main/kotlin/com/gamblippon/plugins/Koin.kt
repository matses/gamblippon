package com.gamblippon.plugins

import com.gamblippon.domain.usecase.*
import com.gamblippon.infra.secondary.database.PlayerRepository
import com.gamblippon.infra.secondary.database.PointRepository
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.koin() {
    install(Koin) {
        slf4jLogger()
        modules(diModules)
    }
}

val diModules = module {
    single<PlayerManagement> { PlayerManagementImpl(get(), get())}
    single<PointManagement> { PointManagementImpl(get())}
    single<RankingManagement> { RankingManagementImpl(get())}
    single<PlayerPort> { PlayerRepository() }
    single<PointPort> { PointRepository() }
}