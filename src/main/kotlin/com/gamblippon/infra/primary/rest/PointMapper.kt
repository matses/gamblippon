package com.gamblippon.infra.primary.rest

import com.gamblippon.domain.model.Point


class PointMapper {
    companion object {
        fun Point.toDto() : PointDto = PointDto(id, playerId, total)
    }
}