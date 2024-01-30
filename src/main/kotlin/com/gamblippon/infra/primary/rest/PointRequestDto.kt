package com.gamblippon.infra.primary.rest

import kotlinx.serialization.Serializable

@Serializable
data class PointRequestDto(val playerId: String, val points: Int) {
}