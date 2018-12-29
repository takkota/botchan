package net.onlybiz.botchan.controller.api

import net.onlybiz.botchan.model.api.parameter.AppUserLineGroupSaveParameter
import net.onlybiz.botchan.model.api.parameter.BasicParameter
import net.onlybiz.botchan.model.api.response.AppUserLineGroupResponse
import net.onlybiz.botchan.model.api.response.AppUserLineGroupListResponse
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
    fun getAppUserLineGroup(@RequestBody body: BasicParameter): AppUserLineGroupListResponse? {
        val lineGroups = userService.getAppUserLineGroups(body.userId)?.map {
            AppUserLineGroupResponse(
                    it.id,
                    it.lineGroup?.id,
                    it.displayName
            )
        }
        return AppUserLineGroupListResponse(lineGroups ?: listOf())
    }

    // app_user_line_groupを保存・更新
    @RequestMapping(value = ["/save"], method = [RequestMethod.POST])
    fun saveAppUserLineGroup(@RequestBody body: AppUserLineGroupSaveParameter): AppUserLineGroupResponse? {
        val userLineGroup = if (body.id != null && body.displayName != null) {
            // 表示名更新
            userService.saveGroupDisplayName(body.id!!, body.displayName!!)
        } else {
            // 新規登録(userId, lineGroupId の紐付け)
            userService.saveAppUserAndLineGroupId(body.userId, body.lineGroupId, body.displayName!!)
        }
        return AppUserLineGroupResponse(
                id = userLineGroup?.id,
                lineGroupId = userLineGroup?.lineGroup?.id,
                displayName = userLineGroup?.displayName
        )
    }
}
