package ch.abb.findahelper

import adapter.persistence.database.repository.UserRepository
import adapter.routes.configureRouting
import ch.abb.findahelper.application.interactor.UserInteractor
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.typesafe.config.ConfigFactory
import config.DatabaseConfig
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import java.io.File

fun main() {
    println("Hallo backend")
    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            json()
        }
        install(Authentication) {
            jwt("auth-jwt") {
                val config = ConfigFactory.load()
                val secret = config.getString("jwt.secret")
                val algorithm = Algorithm.HMAC256(secret)
                verifier(
                    JWT.require(algorithm)
                        .withSubject("UserAuth")
                        .build())
                validate { credential ->
                    if (credential.payload.getClaim("email").asString().isNotEmpty()) {
                        JWTPrincipal(credential.payload)
                    } else null
                }
            }
        }
        install(CORS){
            /*for developement*/
            anyHost()
            /*for produktion*/
            /*allowHost("findahelper.ch")
            allowHost("www.findahelper.ch")*/
            allowMethod(HttpMethod.Options)
            allowHeader(HttpHeaders.ContentType)
            allowHeader(HttpHeaders.Authorization)
            allowMethod(HttpMethod.Get)
            allowMethod(HttpMethod.Post)
            allowMethod(HttpMethod.Put)
            allowMethod(HttpMethod.Delete)
            allowCredentials = true
        }
        DatabaseConfig.init(this)
        val userInteractor = UserInteractor(UserRepository())

        configureRouting(userInteractor)
        println("server is running")
    }.start(wait = true)
}