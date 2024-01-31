package com.gamblippon.infra.primary.rest

import com.gamblippon.domain.model.Ranking

class RankingMapper {
    companion object {
        fun Ranking.toDto() : RankingDto = RankingDto(playerId, nickname, points, position)
    }
}