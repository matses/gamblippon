package com.gamblippon.domain.usecase

import com.gamblippon.domain.model.Point
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.common.runBlocking
import io.kotest.matchers.shouldBe
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PointManagementImplTest {

    @MockK
    lateinit var rankingPort : PointPort

    @InjectMockKs
    lateinit var rankingManagement : PointManagementImpl

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `should add points`(): Unit = runBlocking {
        val playerId = "idPlayer1"
        val point = Point("1", "idPlayer1", 3)
        val total = 7
        coEvery { rankingPort.getFromPlayerId(playerId) } returns point;
        coEvery { rankingPort.updateTotal(playerId, total) } returns true;

        rankingManagement.add(playerId, 4)

        coVerify { rankingPort.getFromPlayerId(playerId) }
        coVerify { rankingPort.updateTotal(playerId, total) }
    }

    @Test
    fun `should fail to add points when player is unknown`(): Unit = runBlocking {
        val playerId = "idPlayer1"
        val point = Point("1", playerId, 3)
        val total = 7
        coEvery { rankingPort.getFromPlayerId(playerId) } returns null;
        //every { rankingPort.updateTotal(playerId, total) } returns Unit;

        val exception = shouldThrowExactly<PlayerNotFoundException>{
            rankingManagement.add(playerId, 4)
        }
        exception.message shouldBe "Player ${playerId} not found"
    }

    @Test
    fun `should update total`(): Unit = runBlocking {
        val playerId = "idPlayer1"
        val total = 8
        coEvery { rankingPort.updateTotal(playerId, total) } returns true;

        rankingManagement.updateTotal(playerId, total)

        coVerify { rankingPort.updateTotal(playerId, total) }
    }
}