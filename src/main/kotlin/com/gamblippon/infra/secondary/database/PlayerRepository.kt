package com.gamblippon.infra.secondary.database

import com.gamblippon.domain.model.Player
import com.gamblippon.domain.usecase.PlayerPort
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert

class PlayerRepository:PlayerPort{

    override suspend fun save(player: Player): Player = DatabaseConfiguration.dbQuery {
        val insertStatement = PlayerDao.Player.insert {
            it[id] = player.id
            it[nickname] = player.nickname
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::toPlayer)!!
    }

    private fun toPlayer(row: ResultRow): Player = Player(
        nickname = row[PlayerDao.Player.nickname],
        id = row[PlayerDao.Player.id]
    )

}
