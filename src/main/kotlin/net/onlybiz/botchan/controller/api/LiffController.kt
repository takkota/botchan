package net.onlybiz.botchan.controller.api

import net.onlybiz.botchan.database.TestRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMethod




@Controller
@RequestMapping(value = ["/liff"])
class LiffController {

    @RequestMapping(value = "/", method = [RequestMethod.GET])
    fun linkAction(model: Model): String {
        model.addAttribute("message", "Hello Springboot")
        return "linkLiff"
    }

}
