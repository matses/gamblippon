package com.gamblippon.infra.secondary.database

import com.gamblippon.domain.model.Player
import com.gamblippon.domain.usecase.PlayerPort

class PlayerRepository:PlayerPort{

    override fun save(nickname: String): Player {
        return Player(nickname);
    }

}
