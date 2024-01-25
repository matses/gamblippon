package com.gamblippon.domain.usecase

import com.gamblippon.domain.model.Player

interface PlayerPort {

    fun save(player: Player): Player
}