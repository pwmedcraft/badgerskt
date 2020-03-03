package badgerskt.config

import com.typesafe.config.Config

final data class ApplicationConfiguration(val port: Int) {
    constructor (config: Config): this(
        config.getInt("http.port")
    )
}