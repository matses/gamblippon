package com.gamblippon.infra.secondary.database

import org.jetbrains.exposed.sql.Table

object Players : Table() {
    val id = varchar("id", 128)
    val nickname = varchar("nickname", 128)

    override val primaryKey = PrimaryKey(id)
}