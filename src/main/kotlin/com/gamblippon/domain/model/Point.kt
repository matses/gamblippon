package com.gamblippon.domain.model

import java.util.*

class Point constructor(val id:String, val playerId: String, val total:Int) {

    constructor(playerId: String) : this(generateId(), playerId, 0)
    companion object {
        private fun generateId(): String {
            return UUID.randomUUID().toString()
        }
    }

}
