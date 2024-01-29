package com.gamblippon.domain.usecase

import com.gamblippon.domain.model.Player

class PlayerManagementImpl(private val playerPort: PlayerPort) : PlayerManagement {

    override suspend fun add(player: Player): Player {
        return playerPort.save(player)
    }

}