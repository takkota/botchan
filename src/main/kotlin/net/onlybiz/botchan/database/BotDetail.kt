package net.onlybiz.botchan.database

import com.google.cloud.storage.*
import com.linecorp.bot.model.message.ImageMessage
import javax.persistence.*
import javax.validation.constraints.NotNull
import com.linecorp.bot.model.message.Message
import com.linecorp.bot.model.objectmapper.ModelObjectMapper
import javax.persistence.AttributeConverter
import com.google.cloud.storage.BlobId
import org.springframework.cglib.core.CollectionUtils.bucket
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.StorageClient
import java.io.FileInputStream






@Entity
@Table(name = "bot_detail")
data class BotDetail(
        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        var id: Long? = null,

        @ManyToMany(cascade = [CascadeType.PERSIST]) // CascadeType.Allだとdelete時にgroupまで消されるので
        var lineGroups: List<LineGroup>? = null,

        @ManyToOne(cascade = [CascadeType.ALL], optional = false)
        @NotNull
        var appUser: AppUser? = null,

        @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "botDetail")
        var botReplyCondition: BotReplyCondition? = null,

        @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "botDetail")
        var botPushSchedule: BotPushSchedule? = null,

        @Column(nullable = false)
        @Convert(converter = MessageConverter::class)
        var message: Message? = null

): CommonEntity()

class MessageConverter: AttributeConverter<Message, String> {
        // LineSdkで使用しているObjectMapperを使う
        override fun convertToDatabaseColumn(message: Message): String {
                val mapper = ModelObjectMapper.createNewObjectMapper()
                return mapper.writeValueAsString(message)
        }
        override fun convertToEntityAttribute(source: String): Message {
                val mapper = ModelObjectMapper.createNewObjectMapper()
                return mapper.readValue(source, Message::class.java)
        }
}
