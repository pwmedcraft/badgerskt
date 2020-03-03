package badgerskt.config

import arrow.core.*
import badgerskt.logging.Loggable
import kotlin.system.exitProcess

sealed class Environment {
    abstract val configFileName: String
    abstract val title: String

    companion object : Loggable {

        fun decide(env: String): Either<String, Environment> {
            return when (env) {
                "local" -> Right(Local)
                "dev" -> Right(Dev)
                else -> Left("Unrecognised config value: $env")
            }
        }

        fun fromProperties(): Environment {
            return Right(System.getProperty("config")).leftIfNull { "No config value" }.flatMap { e -> decide(e) }.fold(
                { it ->
                    val msg = """
                        |No valid config parameter found: 
                        |
                        |$it was specified so the system will shutdown.
                        |
                        |Please set system property config like so:
                        |
                        |  -Dconfig=ENV
                        |
                        |Where ENV is one of:
                        |local
                        |dev
                    """.trimMargin()
                    logger().error(msg)
                    println(msg)
                    exitProcess(1)
                },
                { env -> env }
            )
        }
    }

}

object Local : Environment() {
    override val configFileName = "local"
    override val title = "Local"
}

object Dev : Environment() {
    override val configFileName = "dev"
    override val title = "Dev"
}

