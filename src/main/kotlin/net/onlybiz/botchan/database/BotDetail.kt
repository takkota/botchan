package net.onlybiz.botchan.database

import javax.persistence.*
import javax.validation.constraints.NotNull
import com.linecorp.bot.model.message.Message
import com.linecorp.bot.model.objectmapper.ModelObjectMapper
import javax.persistence.AttributeConverter


@Entity
@Table(name = "bot_detail")
data class BotDetail(
        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        var id: Long? = null,

        @ManyToMany(cascade = [CascadeType.ALL])
        var rooms: List<Room>? = null,

        @ManyToOne(cascade = [CascadeType.ALL])
        @NotNull
        var appUser: AppUser? = null,

        @OneToOne(mappedBy = "botDetail", cascade = [CascadeType.ALL])
        var botReplyCondition: BotReplyCondition? = null,

        @OneToOne(mappedBy = "botDetail", cascade = [CascadeType.ALL])
        var botPushCondition: BotPushCondition? = null,

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
