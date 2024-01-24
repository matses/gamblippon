package com.gamblippon.domain.usecase

import com.gamblippon.domain.model.Player

interface PlayerPort {

    fun save(nickname: String): Player
}