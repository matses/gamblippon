package com.gamblippon.domain.usecase

import com.gamblippon.domain.model.Ranking



class RankingManagementImpl(private val pointPort:PointPort): RankingManagement {

    override suspend fun getRanking(): List<Ranking> {
        return pointPort.getRanking()
    }

    override suspend fun get(playerId: String): Ranking {
        // @TODO make port implem passed
        //return pointPort.getRanking(playerId)
        return getRanking().filter { it.playerId == playerId }.first()
    }




}