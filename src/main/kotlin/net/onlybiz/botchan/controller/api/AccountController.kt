package net.onlybiz.botchan.controller.api

import net.onlybiz.botchan.database.AppUser
import net.onlybiz.botchan.database.AppUserRepository
import net.onlybiz.botchan.database.TestRepository
import net.onlybiz.botchan.model.api.request.LinkTokenParameter
import net.onlybiz.botchan.model.api.response.LinkAppResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/account")
class AccountController {

    @Autowired
    lateinit var appUserRepository: AppUserRepository

    // Lineと連携するためのリンクを発行する。
    @RequestMapping(value = ["/link"], method = [RequestMethod.POST])
    fun generateLinkAppUrl(@RequestBody body: LinkTokenParameter): LinkAppResponse {
        val uuid = UUID.randomUUID().toString()
        val nonce = Base64.getEncoder().encodeToString(uuid.toByteArray())
        // userIdとnonceを紐付ける
        appUserRepository.save(AppUser(body.userId, null, nonce, null))
        return LinkAppResponse("https://access.line.me/dialog/bot/accountLink?linkToken=${body.linkToken}&nonce=${nonce}")
    }
}
