package com.gamblippon.domain.usecase

import com.gamblippon.domain.model.Ranking

interface RankingManagement {

    suspend fun get(playerId: String): Ranking

    suspend fun getRanking(): List<Ranking>
}