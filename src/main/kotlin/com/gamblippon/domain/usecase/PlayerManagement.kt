package com.gamblippon.domain.usecase

import com.gamblippon.domain.model.Player

interface PlayerManagement {

        suspend fun add(player: Player): Player;
}