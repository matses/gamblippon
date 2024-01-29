package com.gamblippon.infra.secondary.database

import TestDatabase
import com.gamblippon.domain.model.Player
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.kotest.common.runBlocking
import io.kotest.matchers.shouldNotBe
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.testcontainers.containers.PostgreSQLContainer

class PlayerRepositoryTest {

    val playerRepository = PlayerRepository()

    @Test
    fun `should insert Player`() : Unit = runBlocking {
        val p = playerRepository.save(Player("Hector le Castor", null))
        p.id shouldNotBe null
    }

    companion object {
        @JvmStatic
        @BeforeAll
        fun setUp(): Unit {
            TestDatabase
        }
    }
}
