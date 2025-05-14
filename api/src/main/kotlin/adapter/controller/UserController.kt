package adapter.controller

import ch.abb.findahelper.application.interactor.UserInteractor
import common.LoggerService
import io.ktor.server.application.*
import io.ktor.server.routing.*


fun Route.userRoutes(userInteractor: UserInteractor) {
    route("/user") {
        post("/register") {
            LoggerService.debugLog(call.toString())
        }

        route("/auth") {
            post("/google") {
            }

            post("/local") {

            }
        }
    }

}
