package net.onlybiz.botchan.database

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.annotation.Transactional


@Service
class UserService {

    @Autowired
    private lateinit var appUserRepository: AppUserRepository

    @Autowired
    private lateinit var appUserGroupRepository: AppUserGroupRepository

    @Autowired
    private lateinit var groupRepository: GroupRepository

    // user_idとgroup_idを紐付ける
    @Transactional
    fun saveAppUserGroupFromLineId(lineId: String, groupId: String): Boolean {
        val appUser = appUserRepository.findByLineId(lineId)
        appUser ?: return false
        println("testd:lineUser" + appUser?.lineId)

        val alreadyCombined = appUser?.appUserGroups?.count { it.group?.id == groupId } ?: 0 > 0
        println("testd:alreadyCombined" + alreadyCombined)
        if (appUser != null && !alreadyCombined) {
            println("testd:save")
            appUserGroupRepository.save(AppUserGroup(appUser = appUser, group = Group(id = groupId)))
            return true
        }
        return false
    }
}