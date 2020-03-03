package badgerskt.status

import badgerskt.logging.Loggable
import java.lang.management.ManagementFactory.getRuntimeMXBean

import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

object Uptime : Loggable {
    @ExperimentalTime
    fun uptime(): String {
        logger().info("")
        val u = getRuntimeMXBean().uptime.toDuration(DurationUnit.MILLISECONDS)
        return "${Math.floor(u.inDays).toInt()} days, ${Math.floor(u.inHours % 24).toInt()} hours, ${Math.floor(u.inMinutes % 60).toInt()} minutes and ${Math.floor(u.inSeconds % 60).toInt()} seconds"
    }
}