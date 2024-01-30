package com.gamblippon.infra.secondary.database

import com.gamblippon.domain.model.Point
import com.gamblippon.domain.usecase.PointPort
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class PointRepository:PointPort {
    override suspend fun save(point: Point): Point? = DatabaseConfiguration.dbQuery {
            val insertStatement = Points.insert {
                it[Points.id] = point.id
                it[Points.playerId] = point.playerId
                it[Points.total] = point.total
            }
            insertStatement.resultedValues?.singleOrNull()?.let(::toPoint)
    }

    override suspend fun getFromPlayerId(id: String): Point ? = DatabaseConfiguration.dbQuery {
        Points
            .select { Points.playerId eq id }
            .map(::toPoint)
            .singleOrNull()
    }

    private fun toPoint(row: ResultRow): Point = Point(
        id = row[Points.id],
        playerId = row[Points.playerId],
        total = row[Points.total]
    )

}
