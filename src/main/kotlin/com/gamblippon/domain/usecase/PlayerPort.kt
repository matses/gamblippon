package com.gamblippon.domain.usecase

import com.gamblippon.domain.model.Player

interface PlayerPort {

    suspend fun save(player: Player): Player

    suspend fun get(id: String): Player?
}