package com.gamblippon.infra.primary.rest

import com.gamblippon.domain.model.Ranking
import com.gamblippon.domain.usecase.PointManagement
import com.gamblippon.domain.usecase.PointManagementImpl
import com.gamblippon.domain.usecase.RankingManagement
import com.gamblippon.domain.usecase.RankingManagementImpl
import com.gamblippon.plugins.configureRouting
import com.gamblippon.plugins.configureSerialization
import io.kotest.matchers.shouldBe
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

class RankingRouteKtTest {

    val mockRankingManagement : RankingManagement = mockk<RankingManagementImpl>()

    @BeforeEach
    fun initDI(){
        val testModule = module {
            single<RankingManagement> { mockRankingManagement }
        }
        startKoin { modules(testModule) }
    }

    @AfterEach
    fun stopDI(){
        stopKoin()
    }

    fun testBaseApplication(block: suspend ApplicationTestBuilder.() -> Unit) = testApplication {
        environment {
            config = ApplicationConfig("application-test.yaml")
        }
        application {
            module {
                configureRouting()
                configureSerialization()
            }
        }
        block()
    }

    @Test
    fun `should get player ranking successfully`() =
        testBaseApplication {
            val client = createClient {
                install(ContentNegotiation) {
                    json()
                }
            }

            val expectedId = "id1"
            val expectedNickname = "George Abitbol"
            val expectedPoints = 34
            val expectedPosition = 1L
            coEvery { mockRankingManagement.get(expectedId ) } returns Ranking("id1", expectedNickname, expectedPoints, expectedPosition);
            val response = client.get("/ranking/id1") {
                contentType(ContentType.Application.Json)
            }
            response.status shouldBe HttpStatusCode.OK
            var result = response.body<RankingDto>()

            result.playerId shouldBe expectedId
            result.points shouldBe expectedPoints
            result.nickname shouldBe expectedNickname
            result.position shouldBe expectedPosition
        }

    @Test
    fun `should get ranking successfully`() =
        testBaseApplication {
            val client = createClient {
                install(ContentNegotiation) {
                    json()
                }
            }

            coEvery { mockRankingManagement.getRanking( ) } returns rankingFixture();
            val response = client.get("/ranking") {
                contentType(ContentType.Application.Json)
            }
            response.status shouldBe HttpStatusCode.OK
            var result = response.body<List<RankingDto>>()

            result.get(0).playerId shouldBe "id1"
            result.get(0).points shouldBe 34
            result.get(0).nickname shouldBe "George Abitbol"
            result.get(0).position shouldBe 1

            result.get(1).playerId shouldBe "id2"
            result.get(2).playerId shouldBe "id3"
            result.get(3).playerId shouldBe "id4"
        }



    fun rankingFixture():List<Ranking>  {
        val r1 = Ranking("id1", "George Abitbol", 34, 1)
        val r2 = Ranking("id2", "Michael Jordan", 23, 2)
        val r3 = Ranking("id3", "Roger Federer", 7, 4)
        val r4 = Ranking("id4", "Bradd Pitbull", 15, 3)
        return listOf(r1, r2, r3, r4)
    }

}