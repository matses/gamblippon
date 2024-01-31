package com.gamblippon.infra.primary.rest

import kotlinx.serialization.Serializable

@Serializable
data class RankingDto(val playerId:String, val nickname:String, val points:Int, val position:Long) {
}