package com.gamblippon.infra.primary.rest

import kotlinx.serialization.Serializable

@Serializable
data class PointDto(val id: String, val playerId: String, val total: Int) {
}