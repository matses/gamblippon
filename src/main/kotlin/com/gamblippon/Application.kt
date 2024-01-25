package com.gamblippon

import com.gamblippon.plugins.*
import io.ktor.server.application.*
import io.ktor.server.plugins.doublereceive.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    //configureDatabases()
    configureHTTP()
    configureRouting()
    koin()
    install(DoubleReceive)
}
