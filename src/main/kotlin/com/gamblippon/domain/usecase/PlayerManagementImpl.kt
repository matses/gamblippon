package com.gamblippon.domain.usecase

import com.gamblippon.domain.model.Player
import com.gamblippon.domain.model.Point

class PlayerManagementImpl(private val playerPort: PlayerPort, private val pointPort : PointPort) : PlayerManagement {

    override suspend fun add(player: Player): Player {
        val savedPlayer = playerPort.save(player)
        pointPort.save(Point(savedPlayer.id))
        return savedPlayer
    }

}