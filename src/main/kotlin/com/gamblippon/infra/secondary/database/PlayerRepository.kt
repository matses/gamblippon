package com.gamblippon.infra.secondary.database

import com.gamblippon.domain.model.Player
import com.gamblippon.domain.usecase.PlayerPort
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class PlayerRepository:PlayerPort{

    override suspend fun save(player: Player): Player = DatabaseConfiguration.dbQuery {
        val insertStatement = Players.insert {
            it[id] = player.id
            it[nickname] = player.nickname
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::toPlayer)!!
    }

    override suspend fun get(id: String): Player? = DatabaseConfiguration.dbQuery {
        Players
            .select { Players.id eq id }
            .map(::toPlayer)
            .singleOrNull()
    }

    private fun toPlayer(row: ResultRow): Player = Player(
        nickname = row[Players.nickname],
        id = row[Players.id]
    )

}
