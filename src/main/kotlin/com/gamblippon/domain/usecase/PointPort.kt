package com.gamblippon.domain.usecase

import com.gamblippon.domain.model.Point
import com.gamblippon.domain.model.Ranking

interface PointPort {

    suspend fun save(point: Point): Point?

    suspend fun getFromPlayerId(id: String): Point?

    suspend fun updateTotal(playerId: String, total: Int): Boolean

    suspend fun getRanking(): List<Ranking>

    suspend fun getRanking(playerId : String): Ranking?
}