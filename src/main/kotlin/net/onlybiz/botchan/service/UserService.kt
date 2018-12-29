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
    private lateinit var botDetailRepository: BotDetailRepository

    // lineGroupを取得する
    @Transactional
    fun getAppUserLineGroups(userId: String): List<AppUserLineGroup> {
        val user = appUserRepository.findById(userId)
        return if (user.isPresent) {
            user.get().appUserLineGroups ?: listOf()
        } else {
            listOf()
        }
    }

    // userIdを保存する
    @Transactional
    fun saveAppUserId(userId: String): AppUser? {
        val user = appUserRepository.findById(userId)
        return if (!user.isPresent) {
            appUserRepository.save(AppUser(id = userId))
        } else {
            null
        }
    }

    // userIdを保存する
    @Transactional
    fun saveAppUserIdAndLineId(userId: String, lineId: String) {
        appUserRepository.findByLineId(lineId)?.let { appUser ->
            // すでに存在するLINE IDで来たときは、レコードを削除してから保存する
            appUserRepository.deleteById(appUser.id)
            appUserRepository.flush()
        }
        appUserRepository.save(AppUser(id = userId, lineId = lineId, linkDateTime = Date()))
    }

    // userIdとline_group_idを紐付ける
    @Transactional
    fun saveAppUserAndLineGroupId(userId: String, groupId: String, displayName: String): AppUserLineGroup? {
        val appUser = appUserRepository.findById(userId).get()

        val alreadyCombined = appUser.appUserLineGroups?.count { it.lineGroup?.id == groupId } ?: 0 > 0
        if (!alreadyCombined) {
            return appUserLineGroupRepository.save(AppUserLineGroup(appUser = appUser, lineGroup = LineGroup(id = groupId), displayName = displayName))
        }
        return null
    }

    // groupの表示名を更新する
    @Transactional
    fun saveGroupDisplayName(id: Long, displayName: String): AppUserLineGroup? {
        val appUserGroup = AppUserLineGroup(id = id, displayName = displayName)
        return appUserLineGroupRepository.save(appUserGroup)
    }

    @Transactional(readOnly = true)
    fun test() {
        val botReplies = botDetailRepository.findAll()
        if (botReplies.size > 0) {
        }
    }
}