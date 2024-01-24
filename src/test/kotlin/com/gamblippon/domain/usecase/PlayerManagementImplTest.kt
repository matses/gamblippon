package com.gamblippon.domain.usecase

import com.gamblippon.domain.model.Player
import com.gamblippon.infra.secondary.database.PlayerRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PlayerManagementImplTest {

    @MockK
    lateinit var playerRepository : PlayerRepository

    @InjectMockKs
    private var playerManagement = PlayerManagementImpl()

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `should add player`() {
        var expectedNickname = "Johnny B Goode"
        var expectedPlayer = Player(expectedNickname)
        every { playerRepository.save(expectedNickname) } returns expectedPlayer;

        var effectivePlayer = playerManagement.add(expectedNickname)

        assertEquals(expectedPlayer, effectivePlayer)
    }

}