package badgerskt

import badgerskt.config.ApplicationConfiguration
import badgerskt.config.Environment
import badgerskt.logging.Loggable
import badgerskt.metrics.MetricRegistryWithJvmGauges
import badgerskt.metrics.Timers
import badgerskt.routes.badgers
import badgerskt.routes.indexPage
import badgerskt.routes.metrics
import badgerskt.routes.status
import com.typesafe.config.ConfigFactory
import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.routing.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.*
import kotlin.time.ExperimentalTime

@ExperimentalTime
fun main(args: Array<String>) {
    BadgersApp.releaseTheBadgers()
}

object BadgersApp : Loggable {

    @ExperimentalTime
    fun releaseTheBadgers() {
        logger().info(
            """
                |
                |__/\\\\\\\\\\\\\__________________________/\\\_____________________________________________________________________________________        
                | _\/\\\/////////\\\_______________________\/\\\__________________________________________________________/\\\_______________________       
                |  _\/\\\_______\/\\\_______________________\/\\\____/\\\\\\\\____________________________________________\/\\\_____________/\\\______      
                |   _\/\\\\\\\\\\\\\\___/\\\\\\\\\___________\/\\\___/\\\////\\\_____/\\\\\\\\___/\\/\\\\\\\___/\\\\\\\\\\_\/\\\\\\\\_____/\\\\\\\\\\\_     
                |    _\/\\\/////////\\\_\////////\\\_____/\\\\\\\\\__\//\\\\\\\\\___/\\\/////\\\_\/\\\/////\\\_\/\\\//////__\/\\\////\\\__\////\\\////__    
                |     _\/\\\_______\/\\\___/\\\\\\\\\\___/\\\////\\\___\///////\\\__/\\\\\\\\\\\__\/\\\___\///__\/\\\\\\\\\\_\/\\\\\\\\/______\/\\\______   
                |      _\/\\\_______\/\\\__/\\\/////\\\__\/\\\__\/\\\___/\\_____\\\_\//\\///////___\/\\\_________\////////\\\_\/\\\///\\\______\/\\\_/\\__  
                |       _\/\\\\\\\\\\\\\/__\//\\\\\\\\/\\_\//\\\\\\\/\\_\//\\\\\\\\___\//\\\\\\\\\\_\/\\\__________/\\\\\\\\\\_\/\\\_\///\\\____\//\\\\\___ 
                |        _\/////////////_____\////////\//___\///////\//___\////////_____\//////////__\///__________\//////////__\///____\///______\/////____
                |        
            """.trimMargin()
        )

        val environment = Environment.fromProperties()
        val config = ApplicationConfiguration(ConfigFactory.load(environment.configFileName))

        val metricRegistry = MetricRegistryWithJvmGauges.metricRegistry()
        val timers = Timers(metricRegistry)

        val server = embeddedServer(Netty, config.port) {
            routing {
                route("/api/badgers") {
                    badgers(timers)
                }
                route("/metrics") {
                    metrics(metricRegistry)
                }
                route("/status") {
                    status()
                }
                route("/") {
                    indexPage(timers)
                }
            }
        }
        server.start(wait = true)
    }

}
//
//fun Application.module() {
//    install(Locations)
//}

//@location("/")
//class IndexPage()