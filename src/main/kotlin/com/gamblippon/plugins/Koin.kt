package com.gamblippon.plugins

import com.gamblippon.domain.usecase.PlayerManagement
import com.gamblippon.domain.usecase.PlayerManagementImpl
import com.gamblippon.domain.usecase.PlayerPort
import com.gamblippon.infra.secondary.database.PlayerRepository
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
    single<PlayerManagement> { PlayerManagementImpl(get())}
    single<PlayerPort> { PlayerRepository() }
}