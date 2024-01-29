package com.gamblippon.infra.secondary.database

import com.gamblippon.domain.model.Player
import org.jetbrains.exposed.sql.ResultRow

class PlayerMapperDao {

    companion object {
        fun Player.toDao() : PlayerDao = PlayerDao(id, nickname)
        fun PlayerDao.toDomain(): Player = Player(nickname, id)


    }
}