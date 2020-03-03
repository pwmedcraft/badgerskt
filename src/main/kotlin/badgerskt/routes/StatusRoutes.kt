package badgerskt.routes

import badgerskt.BuildInfo
import badgerskt.status.Uptime
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import kotlin.time.ExperimentalTime

@ExperimentalTime
fun Route.status() {
    get("/version") {
        call.respond(HttpStatusCode.OK, BuildInfo.VERSION)
    }
    get() {
        call.respondText(
            """
             |app name      ${BuildInfo.APP_NAME}
             |version       ${BuildInfo.VERSION}
             |commit        ${BuildInfo.GIT_COMMIT_HASH}
             |build time    ${StatusRoutes.buildTime.toString()}
             |kotlinVersion ${KotlinVersion.CURRENT.toString()}
             |timestamp     ${LocalDateTime.now()}
             |uptime        ${Uptime.uptime()}
             |
           """.trimMargin(),
            ContentType.Text.Plain,
            HttpStatusCode.OK
        )
    }
    get("/json") {
        val moshi = Moshi.Builder().build()
        val t = Types.newParameterizedType(Map::class.java, String::class.java, String::class.java)
        val adapter = moshi.adapter<Map<String, String>>(t)
        call.respondText(
            adapter.toJson(mapOf(
                "app name" to BuildInfo.APP_NAME,
                "version" to BuildInfo.VERSION,
                "commit" to BuildInfo.GIT_COMMIT_HASH,
                "build time" to StatusRoutes.buildTime.toString(),
                "kotlinVersion" to KotlinVersion.CURRENT.toString(),
                "timestamp" to LocalDateTime.now().toString(),
                "uptime" to Uptime.uptime()
            )),
            ContentType.Application.Json,
            HttpStatusCode.OK
        )
    }
}

object StatusRoutes {
    val buildTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(BuildInfo.BUILD_TIME), ZoneId.systemDefault())
}