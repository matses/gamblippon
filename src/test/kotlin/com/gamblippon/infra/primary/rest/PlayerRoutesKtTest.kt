package com.gamblippon.infra.primary.rest

import com.gamblippon.domain.model.Player
import com.gamblippon.domain.usecase.PlayerManagement
import com.gamblippon.domain.usecase.PlayerManagementImpl
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
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

class PlayerRoutesKtTest {

    val mockPlayerManagement : PlayerManagement = mockk<PlayerManagementImpl>()

    @BeforeEach
    fun initDI(){
        val testModule = module {
            single<PlayerManagement> { mockPlayerManagement }
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
    fun shouldPostPlayerSuccessfully() =
        testBaseApplication {
            val client = createClient {
                install(ContentNegotiation) {
                    json()
                }
            }
            var nickname = "Billy the Kick"
            val playerDto = PlayerDto(nickname)
            val expectedId = "123"
            coEvery { mockPlayerManagement.add(any<Player>()) } returns Player(nickname, expectedId);
            val response = client.post("/player") {
                contentType(ContentType.Application.Json)
                setBody(playerDto)
            }

            response.status shouldBe HttpStatusCode.Created
            response.headers.get(HttpHeaders.Location) shouldStartWith("/player/")
            var playerWithId = response.body<PlayerDto>()
            playerWithId.id shouldBe expectedId
            playerWithId.nickname shouldBe nickname
        }


    @Test
    fun shouldFailWhenNicknameNotSet() = testBaseApplication  {
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val response = client.post("/player") {
            contentType(ContentType.Application.Json)
            setBody("{}")
        }
        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

}