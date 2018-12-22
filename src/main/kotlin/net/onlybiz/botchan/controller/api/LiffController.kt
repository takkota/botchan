package net.onlybiz.botchan.controller.api

import net.onlybiz.botchan.settings.DeepLink
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.util.UriComponentsBuilder

@Controller
@RequestMapping(value = ["/liff"])
class LiffController {

    @Autowired
    lateinit var deeplink: DeepLink

    @RequestMapping(value =["/linkAction"], method = [RequestMethod.GET])
    fun linkAction(model: Model, @RequestParam(name = "userId") userId: String): String {
        val linkParam = UriComponentsBuilder.newInstance()
                .host(deeplink.linkBase)
                .path("link_start")
                .queryParam("userId", userId)
                .build()
        val actionUri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(deeplink.domain)
                .path("link_start")
                .queryParam("link", linkParam)
                .queryParam("apn", deeplink.apn)
                .queryParam("ibi", deeplink.ibi)
                .queryParam("isi", deeplink.isi)
                .build()
        model.addAttribute("message", "Hello Springboot")
        model.addAttribute("deeplink", actionUri.toString())
        return "linkAction"
    }

}
