package net.onlybiz.botchan.model.api.response

class AppUserLineGroupListResponse(val lineGroupList: List<AppUserLineGroupResponse>)

data class AppUserLineGroupResponse(
        val id: Long?,
        val lineGroupId: String?,
        val displayName: String?
)
