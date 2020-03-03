package badgerskt.routes

import arrow.core.Option
import arrow.core.Some
import badgerskt.metrics.Timers
import badgerskt.metrics.Timers.Companion.time
import badgerskt.model.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get
import java.time.LocalDate

fun Route.badgers(timers: Timers) {
    get() {
        call.respondText(BadgersRoutes.badgersJson, ContentType.Application.Json, HttpStatusCode.OK)
        // time(timers.allBadgers,
    }
    get("/{badgerName}") {
        val badgerName = call.parameters["badgerName"]
        Option(BadgersRoutes.sett.get(badgerName)).fold(
            { call.respond(HttpStatusCode.NotFound) },
            {
                val moshi = Moshi.Builder().build()
                val adapter = BadgerJsonAdapter(moshi)
                call.respondText(adapter.toJson(it), ContentType.Application.Json, HttpStatusCode.OK) }
        )
        //time(timers.individualBadger,
    }
}

object BadgersRoutes {
    val sett = mapOf(
        "Keith" to Badger("Keith", Some(LocalDate.of(2017, 7, 14)), listOf(Nuts, HedgehogsFeet)),
        "Geoffrey" to Badger("Geoffrey", Some(LocalDate.of(2015, 10, 27)), listOf(Berries, Seeds, HedgehogsFeet)),
        "Barbara" to Badger("Barbara", Some(LocalDate.of(2016, 3, 2)), listOf(HedgehogsFeet))
    )

    val moshi = Moshi.Builder().build()
    val t = Types.newParameterizedType(List::class.java, Badger::class.java)
    val listAdapter = moshi.adapter<List<Badger>>(t)
    val badgersJson: String = listAdapter.toJson(sett.values.toList())
}