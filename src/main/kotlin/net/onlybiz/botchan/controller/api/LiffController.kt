package net.onlybiz.botchan.controller.api

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMethod

@Controller
@RequestMapping(value = ["/liff"])
class LiffController {

    @RequestMapping(value =["/linkAction"], method = [RequestMethod.GET])
    fun linkAction(model: Model): String {
        model.addAttribute("message", "Hello Springboot")
        return "linkAction"
    }

}
