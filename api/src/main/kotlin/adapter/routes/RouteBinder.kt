package adapter.routes

import adapter.controller.*
import ch.abb.findahelper.application.interactor.UserInteractor
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    userInteractor: UserInteractor,
) {
    routing {
        userRoutes(userInteractor)
    }
}
