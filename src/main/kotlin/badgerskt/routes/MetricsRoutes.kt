package badgerskt.routes

import arrow.core.Option
import com.codahale.metrics.MetricRegistry
import com.codahale.metrics.json.MetricsModule
import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get
import java.util.concurrent.TimeUnit

fun Route.metrics(metricRegistry: MetricRegistry) {
    get() {
        MetricsRoutes.metricsResponse(metricRegistry).fold(
            { call.respond(HttpStatusCode.InternalServerError, "Metrics JSON was null")},
            { call.respondText(it, ContentType.Application.Json, HttpStatusCode.OK) }
        )
    }
}

object MetricsRoutes {

    private val defaultMapper = getDefaultMapper()

    private fun getDefaultMapper(): ObjectMapper {
        val module = MetricsModule(TimeUnit.SECONDS, TimeUnit.SECONDS, true)
        return ObjectMapper().registerModule(module)
    }

    fun metricsResponse(registry: MetricRegistry, mapper: ObjectMapper = MetricsRoutes.defaultMapper): Option<String> {
        val writer = mapper.writerWithDefaultPrettyPrinter()
        return Option(writer.writeValueAsString(registry))
    }
}
