package com.gamblippon.domain.model

import java.util.UUID

data class Player private constructor(val nickname: String, val id: String = generateId()) {
    companion object {
        private fun generateId(): String {
            return UUID.randomUUID().toString()
        }
        operator fun invoke(nickname: String, id: String?) = Player(nickname,  id = id ?: generateId())

    }

}
