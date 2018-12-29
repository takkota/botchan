package net.onlybiz.botchan.controller.api

import net.onlybiz.botchan.database.AppUserLineGroup
import net.onlybiz.botchan.model.api.parameter.AppUserLineGroupSaveParameter
import net.onlybiz.botchan.model.api.parameter.BasicParameter
import net.onlybiz.botchan.model.api.response.AppUserLineGroupResponse
import net.onlybiz.botchan.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/lineGroup")
class LineGroupController {

    @Autowired
    lateinit var userService: UserService

    // app_user_line_groupを取得
    @RequestMapping(value = [""], method = [RequestMethod.POST])
    fun getAppUserLineGroup(@RequestBody body: BasicParameter): AppUserLineGroupResponse {
        return AppUserLineGroupResponse(userService.getAppUserLineGroups(body.userId))
    }

    // app_user_line_groupを保存・更新
    @RequestMapping(value = ["/save"], method = [RequestMethod.POST])
    fun saveAppUserLineGroup(@RequestBody body: AppUserLineGroupSaveParameter): AppUserLineGroup? {
        return if (body.id != null && body.displayName != null) {
            // 更新(表示名)
            userService.saveGroupDisplayName(body.id!!, body.displayName!!)
        } else {
            // 新規登録(userId, lineGroupId の紐付け)
            userService.saveAppUserAndLineGroupId(body.userId, body.lineGroupId, body.displayName!!)
        }
    }
}
