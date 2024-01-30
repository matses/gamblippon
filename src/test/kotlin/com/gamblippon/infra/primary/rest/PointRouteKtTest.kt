package com.gamblippon.infra.primary.rest

import com.gamblippon.domain.model.Player
import com.gamblippon.domain.usecase.PlayerManagement
import com.gamblippon.domain.usecase.PlayerManagementImpl
import com.gamblippon.domain.usecase.PointManagement
import com.gamblippon.domain.usecase.PointManagementImpl
import com.gamblippon.plugins.configureRouting
import com.gamblippon.plugins.configureSerialization
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldStartWith
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

class PointRouteKtTest {
    val mockPointManagement : PointManagement = mockk<PointManagementImpl>()

    @BeforeEach
    fun initDI(){
        val testModule = module {
            single<PointManagement> { mockPointManagement }
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
    fun `should post points successfully`() =
        testBaseApplication {
            val client = createClient {
                install(ContentNegotiation) {
                    json()
                }
            }
            val expectedId = "1"
            val points = 3
            val pointRequestDto = PointRequestDto(expectedId, points )
            coEvery { mockPointManagement.add(any<String>(), any<Int>()) } returns Unit;
            val response = client.post("/point") {
                contentType(ContentType.Application.Json)
                setBody(pointRequestDto)
            }
            response.status shouldBe HttpStatusCode.Created
        }

}