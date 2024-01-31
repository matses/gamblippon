package com.gamblippon.infra.secondary.database

import TestDatabase
import com.gamblippon.domain.model.Player
import com.gamblippon.domain.model.Point
import io.kotest.common.runBlocking
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PointRepositoryTest {

    val pointRepository = PointRepository()
    val playerRepository = PlayerRepository()

    lateinit var playerId1:String
    lateinit var playerId2:String
    lateinit var playerId3:String
    lateinit var playerId4:String
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

    fun createPointFixtures() = runBlocking {
        playerId1 = createWithPoints("George Abitbol", 34)
        playerId2 = createWithPoints("Roger Federer", 7)
        playerId3 = createWithPoints("Michael Jordan", 23)
        playerId4 = createWithPoints("Bradd Pitbull", 15)
    }

    @Test
    fun `should retrieve ranking `() : Unit = runBlocking {
        createPointFixtures()
        val rankingClass = pointRepository.getRanking()

        rankingClass.get(0).playerId shouldBe playerId1
        rankingClass.get(1).playerId shouldBe playerId3
        rankingClass.get(2).playerId shouldBe playerId4
        rankingClass.get(3).playerId shouldBe playerId2
    }

    // @TODO make this test pass
    //@Test
    fun `should retrieve player ranking `() : Unit = runBlocking {
        createPointFixtures()
        val playerRanking = pointRepository.getRanking(playerId4)

        playerRanking?.position shouldBe 3
    }

    private suspend fun createWithPoints(nickname: String, points: Int): String {
        val player = playerRepository.save(Player(nickname, null))
        pointRepository.save(Point(player.id))
        pointRepository.updateTotal(player.id, points)
        return player.id
    }
}