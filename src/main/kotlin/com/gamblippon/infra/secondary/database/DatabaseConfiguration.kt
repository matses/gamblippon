package com.gamblippon.infra.secondary.database

import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseConfiguration {
    fun init(environment: ApplicationEnvironment) {
        val driverClassName = environment.config.propertyOrNull("database.driverClassName")?.getString() ?: "org.postgresql.Driver"

        val jdbcURL = environment.config.propertyOrNull("database.jdbcURL")?.getString() ?: "jdbc:postgresql://localhost:5432/gamblippon"
        val user = environment.config.propertyOrNull("database.user")?.getString() ?: "root"
        val psswd = environment.config.propertyOrNull("database.password")?.getString() ?: "password"
        val database = Database.connect(jdbcURL, driverClassName, user, psswd)
        transaction(database) {
            SchemaUtils.create(Players)
            SchemaUtils.create(Points)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction (Dispatchers.IO) { block() }
}