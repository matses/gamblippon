package com.gamblippon.domain.usecase

class PointManagementImpl(private val pointPort:PointPort):PointManagement {
    override suspend fun add(playerId: String, points: Int) {
        val point = pointPort.getFromPlayerId(playerId)?: throw PlayerNotFoundException("Player ${playerId} not found")
        this.updateTotal(playerId, point.total.plus(points))
    }

    /**
     * Safeguard against adding some points by mistake
     */
    override suspend fun updateTotal(playerId: String, total: Int) {
        pointPort.updateTotal(playerId, total)
    }


}