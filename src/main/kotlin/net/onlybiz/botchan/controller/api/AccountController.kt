package net.onlybiz.botchan.controller.api

import net.onlybiz.botchan.database.AppUser
import net.onlybiz.botchan.database.AppUserRepository
import net.onlybiz.botchan.model.api.parameter.BasicParameter
import net.onlybiz.botchan.model.api.parameter.LinkTokenParameter
import net.onlybiz.botchan.model.api.response.LinkUrlResponse
import net.onlybiz.botchan.model.api.response.LinkedResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/account")
class AccountController {

    @Autowired
    lateinit var appUserRepository: AppUserRepository

    // Lineとすでに連携済みか
    @RequestMapping(value = ["/is_linked"], method = [RequestMethod.POST])
    fun isAccountLinked(@RequestBody body: BasicParameter): LinkedResponse? {
        val user = appUserRepository.findById(body.userId)
        return if (user.isPresent) {
            LinkedResponse(user.get().lineId != null)
        } else {
            null
        }
    }

    // Lineと連携するためのリンクを発行する。
    @RequestMapping(value = ["/link_url"], method = [RequestMethod.POST])
    fun generateLinkAppUrl(@RequestBody body: LinkTokenParameter): LinkUrlResponse {
        val uuid = UUID.randomUUID().toString()
        val nonce = Base64.getEncoder().encodeToString(uuid.toByteArray())
        // userIdとnonceを紐付ける
        appUserRepository.save(AppUser(body.userId, null, nonce, null))
        return LinkUrlResponse("https://access.line.me/dialog/bot/accountLink?linkToken=${body.linkToken}&nonce=${nonce}")
    }
}
