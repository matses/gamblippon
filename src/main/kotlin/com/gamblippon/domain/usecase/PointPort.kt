package com.gamblippon.domain.usecase

import com.gamblippon.domain.model.Point

interface PointPort {

    suspend fun save(point: Point): Point?

    suspend fun getFromPlayerId(id: String): Point?

    fun updateTotal(playerId: String, i: Int) {

    }
}