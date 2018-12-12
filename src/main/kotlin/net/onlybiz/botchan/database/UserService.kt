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

    @Autowired
    private lateinit var botReplyRepository: BotReplyRepository

    // user_idとgroup_idを紐付ける
    @Transactional
    fun saveAppUserGroupFromLineId(lineId: String, groupId: String): Boolean {
        val appUser = appUserRepository.findByLineId(lineId)
        println("testd:appUser")

        val alreadyCombined = appUser.appUserGroups?.count { it.group?.id == groupId } ?: 0 > 0
        println("testd:alreadyCombined" + alreadyCombined)
        if (!alreadyCombined) {
            println("testd:save")
            appUserGroupRepository.save(AppUserGroup(appUser = appUser, group = Group(id = groupId)))
            return true
        }
        return false
    }

    @Transactional(readOnly = true)
    fun test() {
        println("testd:test")
        val botReplies = botReplyRepository.findAll()
        println("testd:size" + botReplies.size)
        if (botReplies.size > 0) {
            println("testd:botReplay" + botReplies[0].message)
        }
    }
}