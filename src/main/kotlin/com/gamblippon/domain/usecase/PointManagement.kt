package com.gamblippon.domain.usecase

import java.awt.Point

interface PointManagement {

    suspend fun add(playerId: String, points: Int)

    suspend fun updateTotal(playerId: String, total: Int)
}