package net.onlybiz.botchan.settings

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component


@Component
@ConfigurationProperties(prefix = "line.bot")
class LineSetting {
    lateinit var channelToken: String
    lateinit var channelSecret: String
}


@Component
@ConfigurationProperties(prefix = "server")
class Server {
    lateinit var hostName: String
}

@Component
@ConfigurationProperties(prefix = "deeplink")
class DeepLink {
    lateinit var domain: String
}
