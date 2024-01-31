package com.gamblippon.infra.secondary.database

import org.jetbrains.exposed.sql.Table

object Points : Table() {
    val id = varchar("id", 128)
    val playerId = varchar("player_id", 128).references(Players.id)
    val total = integer("total")

    override val primaryKey = PrimaryKey(id)
}