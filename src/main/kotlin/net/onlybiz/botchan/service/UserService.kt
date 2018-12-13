package net.onlybiz.botchan.service

import net.onlybiz.botchan.database.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class UserService {

    @Autowired
    private lateinit var appUserRepository: AppUserRepository

    @Autowired
    private lateinit var appUserGroupRepository: AppUserGroupRepository

    @Autowired
    private lateinit var groupRepository: GroupRepository

    @Autowired
    private lateinit var botReplyRepository: BotReplyRepository

    // userIdとgroup_idを紐付ける
    @Transactional
    fun saveAppUserAndGroupId(userId: String, groupId: String): Boolean {
        val appUser = appUserRepository.findById(userId).get()

        val alreadyCombined = appUser.appUserGroups?.count { it.group?.id == groupId } ?: 0 > 0
        if (!alreadyCombined) {
            appUserGroupRepository.save(AppUserGroup(appUser = appUser, group = Group(id = groupId)))
            return true
        }
        return false
    }

    // groupの表示名を更新する
    @Transactional
    fun saveGroupDisplayName(id: Long, displayName: String) {
        appUserGroupRepository.save(AppUserGroup(id = id, displayName = displayName))
    }

    // line_userIdとgroup_idを紐付ける(未使用)
    @Transactional
    fun saveAppUserGroupFromLineId(lineId: String, groupId: String): Boolean {
        val appUser = appUserRepository.findByLineId(lineId)

        val alreadyCombined = appUser.appUserGroups?.count { it.group?.id == groupId } ?: 0 > 0
        if (!alreadyCombined) {
            appUserGroupRepository.save(AppUserGroup(appUser = appUser, group = Group(id = groupId)))
            return true
        }
        return false
    }

    @Transactional(readOnly = true)
    fun test() {
        val botReplies = botReplyRepository.findAll()
        if (botReplies.size > 0) {
        }
    }
}