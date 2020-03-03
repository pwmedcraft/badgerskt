package badgerskt.routes

import badgerskt.metrics.Timers
import badgerskt.metrics.Timers.Companion.time
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get

fun Route.indexPage(timers: Timers) {
    get() {
        call.respond(HttpStatusCode.OK,"Hello World")
        // time<Unit>(timers.helloWorld, { call.respond("Hello World") })
    }
}
