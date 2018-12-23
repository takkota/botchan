package net.onlybiz.botchan.controller.api

import net.onlybiz.botchan.settings.DeepLink
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.util.UriComponentsBuilder
import java.net.URLEncoder

@Controller
@RequestMapping(value = ["/liff"])
class LiffController {

    @Autowired
    lateinit var deeplink: DeepLink

    @RequestMapping(value =["/linkAction"], method = [RequestMethod.GET])
    fun linkAction(model: Model, @RequestParam(name = "lineId") lineId: String): String {
        val linkParam = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(deeplink.linkBase)
                .path("linkAccount")
                .queryParam("lineId", lineId)
                .build()
        val actionUri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(deeplink.domain)
                .queryParam("apn", deeplink.apn)
                .queryParam("ibi", deeplink.ibi)
                .queryParam("isi", deeplink.isi)
                .queryParam("link", URLEncoder.encode(linkParam.toString(), "UTF-8"))
                .build()
        model.addAttribute("deeplink", actionUri.toString())
        return "deeplink"
    }

    @RequestMapping(value =["/addGroupAction"], method = [RequestMethod.GET])
    fun addGroupAction(model: Model, @RequestParam(name = "lineGroupId") groupId: String): String {
        val linkParam = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(deeplink.linkBase)
                .path("addGroup")
                .queryParam("lineGroupId", groupId)
                .build()
        val actionUri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(deeplink.domain)
                .queryParam("apn", deeplink.apn)
                .queryParam("ibi", deeplink.ibi)
                .queryParam("isi", deeplink.isi)
                .queryParam("link", URLEncoder.encode(linkParam.toString(), "UTF-8"))
                .build()
        model.addAttribute("deeplink", actionUri.toString())
        return "deeplink"
    }
}
