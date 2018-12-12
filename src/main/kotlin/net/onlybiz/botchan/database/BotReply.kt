package net.onlybiz.botchan.database

import com.linecorp.bot.model.message.TemplateMessage
import java.io.Serializable
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull
import com.google.gson.Gson
import com.linecorp.bot.model.message.Message
import com.linecorp.bot.model.objectmapper.ModelObjectMapper
import sun.plugin2.util.PojoUtil.toJson
import javax.persistence.AttributeConverter




@Entity
@Table(name = "bot_replies")
data class BotReply(
        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        var id: Long? = null,

        @ManyToOne(cascade = [CascadeType.ALL])
        @NotNull
        var group: Group? = null,

        @ManyToOne(cascade = [CascadeType.ALL])
        @NotNull
        var user: AppUser? = null,

        @OneToMany(mappedBy = "botReply", cascade = [CascadeType.ALL]) // 中間テーブルとのリレーション
        var botReplayCondition: List<BotReplyCondition>? = null,

        @Column(nullable = false)
        @Convert(converter = MessageConverter::class)
        var message: Message? = null
): CommonEntity()

class MessageConverter: AttributeConverter<Message, String> {
        override fun convertToDatabaseColumn(message: Message): String {
                val mapper = ModelObjectMapper.createNewObjectMapper()
                return mapper.writeValueAsString(message)
        }
        override fun convertToEntityAttribute(source: String): Message {
                val mapper = ModelObjectMapper.createNewObjectMapper()
                return mapper.readValue(source, Message::class.java)
        }
}

//abstract class Message: Serializable {
//        var type: String? = null
//}
//
//data class TextMessage(
//        var text: String? = null
//): Message()
//
//data class StampMessage(
//        var originalContentUrl: String? = null,
//        var previewImageUrl: String? = null
//): Message()
