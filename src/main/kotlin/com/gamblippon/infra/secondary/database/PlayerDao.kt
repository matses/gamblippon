package com.gamblippon.infra.secondary.database

import org.jetbrains.exposed.sql.*

data class PlayerDao(val id: String, val nickname: String){

    object Player : Table() {
        val id = varchar("id", 128)
        val nickname = varchar("nickname", 128)

        override val primaryKey = PrimaryKey(id)
    }
}