package net.onlybiz.botchan.controller.api

import net.onlybiz.botchan.model.api.parameter.BasicParameter
import net.onlybiz.botchan.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/user")
class AppUserController {

    @Autowired
    lateinit var userService: UserService

    // app_userを保存・更新
    @RequestMapping(value = ["/save"], method = [RequestMethod.POST])
    fun saveAppUser(@RequestBody body: BasicParameter) {
        userService.saveAppUserId(body.userId)
    }
}
