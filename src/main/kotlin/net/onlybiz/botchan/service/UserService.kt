package net.onlybiz.botchan.service

import net.onlybiz.botchan.database.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Service
class UserService {

    @Autowired
    private lateinit var appUserRepository: AppUserRepository

    @Autowired
    private lateinit var appUserLineGroupRepository: AppUserLineGroupRepository

    @Autowired
    private lateinit var lineGroupRepository: LineGroupRepository

    @Autowired
    private lateinit var botDetailRepository: BotDetailRepository

    // userIdを保存する
    @Transactional
    fun saveAppUserId(userId: String) {
        if (!appUserRepository.findById(userId).isPresent) {
            appUserRepository.save(AppUser(id = userId))
        }
    }

    // userIdを保存する
    @Transactional
    fun saveAppUserIdAndLineId(userId: String, lineId: String) {
        appUserRepository.save(AppUser(id = userId, lineId = lineId, linkDateTime = Date()))
    }

    // userIdとline_group_idを紐付ける
    @Transactional
    fun saveAppUserAndLineGroupId(userId: String, groupId: String): Boolean {
        val appUser = appUserRepository.findById(userId).get()

        val alreadyCombined = appUser.appUserLineGroups?.count { it.lineGroup?.id == groupId } ?: 0 > 0
        if (!alreadyCombined) {
            appUserLineGroupRepository.save(AppUserLineGroup(appUser = appUser, lineGroup = LineGroup(id = groupId)))
            return true
        }
        return false
    }

    // groupの表示名を更新する
    @Transactional
    fun saveGroupDisplayName(id: Long, displayName: String) {
        val appUserGroup = AppUserLineGroup(id = id, displayName = displayName)
        appUserLineGroupRepository.save(appUserGroup)
    }

    @Transactional(readOnly = true)
    fun test() {
        val botReplies = botDetailRepository.findAll()
        if (botReplies.size > 0) {
        }
    }
}