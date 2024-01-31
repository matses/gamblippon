package com.gamblippon.infra.secondary.database

import com.gamblippon.domain.model.Point
import com.gamblippon.domain.model.Ranking
import com.gamblippon.domain.usecase.PointPort
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.rowNumber

class PointRepository:PointPort {

    val RANKING_POSITION_ALIAS = "position"
    val rankingPosition = rowNumber().over().orderBy(Points.total, SortOrder.DESC)

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
            .selectAll()
            .where { Points.playerId eq id }
            .map(::toPoint)
            .singleOrNull()
    }

    override suspend fun updateTotal(playerId: String, total: Int): Boolean = DatabaseConfiguration.dbQuery {
        Points.update({ Points.playerId eq playerId }) {
            it[Points.total] = total
        } > 0
    }

    override suspend fun getRanking(): List<Ranking> = DatabaseConfiguration.dbQuery {
        Points
            .select(rankingPosition.alias(RANKING_POSITION_ALIAS), Points.id, Points.total, Points.playerId)
            .map(::toRanking)
    }

    override suspend fun getRanking(playerId: String): Ranking? = DatabaseConfiguration.dbQuery {
        // @TODO make this sub query pass
        val ranking = Points
            .select(rankingPosition.alias(RANKING_POSITION_ALIAS), Points.id, Points.total, Points.playerId)
            .alias("ranking")

        ranking
            .selectAll()
            .where { ranking[Points.playerId] eq playerId }
            .map { r -> toRanking(r, ranking) }
            .singleOrNull()


    }

    private fun toPoint(row: ResultRow): Point = Point(
        id = row[Points.id],
        playerId = row[Points.playerId],
        total = row[Points.total]
    )

    private fun toRanking(row: ResultRow): Ranking = Ranking(
        playerId = row[Points.playerId],
        points = row[Points.total],
        position = row[ExpressionAlias(rankingPosition, "position")],
        nickname = "" // TODO join
    )

    private fun toRanking(row: ResultRow,  alias: QueryAlias): Ranking = Ranking(
        playerId = row[alias[Points.playerId]],
        points = row[alias[Points.total]],
        position = row[ExpressionAlias(rankingPosition, "position")],
        nickname = "" // TODO join
    )

}
