package com.gamblippon.infra.primary.rest

import kotlinx.serialization.Serializable

@Serializable
data class PlayerDto(val nickname: String, val id: String? = null) {



}