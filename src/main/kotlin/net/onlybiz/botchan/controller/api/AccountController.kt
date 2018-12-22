package net.onlybiz.botchan.controller.api

import net.onlybiz.botchan.model.api.parameter.LinkAccountParameter
import net.onlybiz.botchan.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/account")
class AccountController {

    @Autowired
    lateinit var userService: UserService

    // userIdとlineIdを紐付ける
    @RequestMapping(value = ["/link"], method = [RequestMethod.POST])
    fun linkAccount(@RequestBody body: LinkAccountParameter) {
        userService.saveAppUserIdAndLineId(body.userId, body.lineId)
    }
}
