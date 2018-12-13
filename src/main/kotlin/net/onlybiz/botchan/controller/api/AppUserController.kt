package net.onlybiz.botchan.controller.api

import net.onlybiz.botchan.model.api.parameter.AppUserGroupSaveParameter
import net.onlybiz.botchan.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/user")
class AppUserController {

    @Autowired
    lateinit var userService: UserService

    // ボット一覧を取得
    @RequestMapping(value = ["/groups/save"], method = [RequestMethod.POST])
    fun saveAppUserGroup(@RequestBody body: AppUserGroupSaveParameter) {
        if (body.id != null && body.displayName != null) {
            // 表示名更新
            userService.saveGroupDisplayName(body.id!!, body.displayName!!)
        } else {
            // user groupの紐付け
            userService.saveAppUserAndGroupId(body.userId, body.groupId)
        }
    }
}
