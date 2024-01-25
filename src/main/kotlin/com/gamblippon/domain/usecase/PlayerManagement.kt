package com.gamblippon.domain.usecase

import com.gamblippon.domain.model.Player

interface PlayerManagement {

        fun add(player: Player): Player;
}