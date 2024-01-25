package com.gamblippon.domain.usecase

import com.gamblippon.domain.model.Player
import com.gamblippon.infra.secondary.database.PlayerRepository
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PlayerManagementImplTest {

    @MockK
    lateinit var playerRepository : PlayerRepository

    @InjectMockKs
    lateinit var playerManagement : PlayerManagementImpl

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `should add player`() {
        var expectedNickname = "Johnny B Goode"
        var expectedPlayer = Player(expectedNickname, null)
        every { playerRepository.save(expectedPlayer) } returns expectedPlayer;

        var effectivePlayer = playerManagement.add(expectedPlayer)

        effectivePlayer shouldBe expectedPlayer
    }

}