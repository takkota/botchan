package net.onlybiz.botchan.controller.api

import net.onlybiz.botchan.database.AppUser
import net.onlybiz.botchan.database.AppUserRepository
import net.onlybiz.botchan.database.TestRepository
import net.onlybiz.botchan.model.api.parameter.LinkTokenParameter
import net.onlybiz.botchan.model.api.parameter.MessageParameter
import net.onlybiz.botchan.model.api.response.LinkAppResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/bot")
class BotController {

    @Autowired
    lateinit var appUserRepository: AppUserRepository

    // Lineと連携するためのリンクを発行する。
    @RequestMapping(value = ["/message"], method = [RequestMethod.POST])
    fun saveBotReply(@RequestBody body: MessageParameter) {
        println("testd" + body.toString())
        // appUserRepository.save(AppUser(body.userId, null, nonce, null))
    }
}