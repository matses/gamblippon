package com.gamblippon.infra.secondary.database

import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseConfiguration {
    fun init(environment: ApplicationEnvironment) {
        val driverClassName = environment.config.propertyOrNull("database.driverClassName")?.getString() ?: "org.h2.Driver"

        val jdbcURL = environment.config.propertyOrNull("database.jdbcURL")?.getString() ?: "jdbc:h2:file:./build/db"
        val database = Database.connect(jdbcURL, driverClassName)
        transaction(database) {
            SchemaUtils.create(PlayerDao.Player)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction (Dispatchers.IO) { block() }
}