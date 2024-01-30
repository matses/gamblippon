package com.gamblippon.infra.secondary.database

import TestDatabase
import com.gamblippon.domain.model.Player
import com.gamblippon.domain.model.Point
import io.kotest.common.runBlocking
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class PointRepositoryTest {

    val pointRepository = PointRepository()
    val playerRepository = PlayerRepository()

    companion object {
        @JvmStatic
        @BeforeAll
        fun setUp(): Unit {
            TestDatabase
        }
    }

    @Test
    fun `should insert point`() : Unit = runBlocking {
        val player = playerRepository.save(Player("George Abitbol", null))
        val p = pointRepository.save(Point(player.id))

        p?.id shouldNotBe null
        p?.total shouldBe 0
        p?.playerId shouldBe player.id
    }

    @Test
    fun `should retrieve point from player`() : Unit = runBlocking {
        val player = playerRepository.save(Player("George Abitbull", null))
        pointRepository.save(Point(player.id))
        val p = pointRepository.getFromPlayerId(player.id)

        p shouldNotBe null
    }
}