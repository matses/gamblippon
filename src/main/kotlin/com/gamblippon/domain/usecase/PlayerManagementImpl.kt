package com.gamblippon.domain.usecase

import com.gamblippon.domain.model.Player

class PlayerManagementImpl : PlayerManagement {

    lateinit var playerPort: PlayerPort

    override fun add(nickname: String): Player {
        return playerPort.save(nickname)
    }

}