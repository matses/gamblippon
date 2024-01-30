package com.gamblippon.domain.usecase

import com.gamblippon.domain.model.Player
import com.gamblippon.domain.model.Point
import io.kotest.common.runBlocking
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PlayerManagementImplTest {

    @MockK
    lateinit var playerPort : PlayerPort

    @MockK
    lateinit var pointPort : PointPort

    @InjectMockKs
    lateinit var playerManagement : PlayerManagementImpl

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `should add player`(): Unit = runBlocking {
        var expectedNickname = "Johnny B Goode"
        var expectedPlayer = Player(expectedNickname, null)
        coEvery { playerPort.save(expectedPlayer) } returns expectedPlayer;
        coEvery { pointPort.save(any()) } returns Point(expectedPlayer.id);

        var effectivePlayer = playerManagement.add(expectedPlayer)

        effectivePlayer shouldBe expectedPlayer
    }

}