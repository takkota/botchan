package net.onlybiz.botchan.controller.api

import net.onlybiz.botchan.model.api.parameter.AppUserGroupSaveParameter
import net.onlybiz.botchan.model.api.parameter.BasicParameter
import net.onlybiz.botchan.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/user")
class AppUserController {

    @Autowired
    lateinit var userService: UserService

    // app_user_line_groupを保存・更新
    @RequestMapping(value = ["/lineGroups/save"], method = [RequestMethod.POST])
    fun saveAppUserGroup(@RequestBody body: AppUserGroupSaveParameter) {
        if (body.id != null && body.displayName != null) {
            // 更新(表示名)
            userService.saveGroupDisplayName(body.id!!, body.displayName!!)
        } else {
            // 新規登録(userId, lineGroupId の紐付け)
            userService.saveAppUserAndLineGroupId(body.userId, body.lineGroupId)
        }
    }

    // app_userを保存・更新
    @RequestMapping(value = ["/user/save"], method = [RequestMethod.POST])
    fun saveAppUser(@RequestBody body: BasicParameter) {
        userService.saveAppUserId(body.userId)
    }
}
