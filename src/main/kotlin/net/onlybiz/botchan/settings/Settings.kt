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
    lateinit var linkBase: String
    lateinit var apn: String
    lateinit var ibi: String
    lateinit var isi: String
}

@Component
@ConfigurationProperties(prefix = "liff")
class Liff {
    lateinit var linkAction: String
    lateinit var addGroupAction: String
}
