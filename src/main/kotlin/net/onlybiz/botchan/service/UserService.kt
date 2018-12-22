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
    private lateinit var appUserRoomRepository: AppUserRoomRepository

    @Autowired
    private lateinit var roomRepository: RoomRepository

    @Autowired
    private lateinit var botDetailRepository: BotDetailRepository

    // userIdとroom_idを紐付ける
    @Transactional
    fun saveAppUserAndRoomId(userId: String, roomId: String): Boolean {
        val appUser = appUserRepository.findById(userId).get()

        val alreadyCombined = appUser.appUserRooms?.count { it.room?.id == roomId } ?: 0 > 0
        if (!alreadyCombined) {
            appUserRoomRepository.save(AppUserRoom(appUser = appUser, room = Room(id = roomId)))
            return true
        }
        return false
    }

    // roomの表示名を更新する
    @Transactional
    fun saveRoomDisplayName(id: Long, displayName: String) {
        val appUserRoom = AppUserRoom(id = id, displayName = displayName)
        appUserRoomRepository.save(appUserRoom)
    }

    // line_userIdとroom_idを紐付ける(未使用)
    @Transactional
    fun saveAppUserRoomFromLineId(lineId: String, roomId: String): Boolean {
        val appUser = appUserRepository.findByLineId(lineId)

        val alreadyCombined = appUser.appUserRooms?.count { it.room?.id == roomId } ?: 0 > 0
        if (!alreadyCombined) {
            appUserRoomRepository.save(AppUserRoom(appUser = appUser, room = Room(id = roomId)))
            return true
        }
        return false
    }

    @Transactional(readOnly = true)
    fun test() {
        val botReplies = botDetailRepository.findAll()
        if (botReplies.size > 0) {
        }
    }
}