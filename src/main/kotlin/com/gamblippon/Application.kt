package com.gamblippon

import com.gamblippon.infra.secondary.database.DatabaseConfiguration
import com.gamblippon.plugins.configureHTTP
import com.gamblippon.plugins.configureRouting
import com.gamblippon.plugins.configureSerialization
import com.gamblippon.plugins.koin
import io.ktor.server.application.*
import io.ktor.server.plugins.doublereceive.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureHTTP()
    configureRouting()
    koin()
    install(DoubleReceive)
    DatabaseConfiguration.init(environment)
}
