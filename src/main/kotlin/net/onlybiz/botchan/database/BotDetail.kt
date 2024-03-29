package net.onlybiz.botchan.database

import javax.persistence.*
import javax.validation.constraints.NotNull
import com.linecorp.bot.model.message.Message
import com.linecorp.bot.model.objectmapper.ModelObjectMapper
import javax.annotation.Nullable
import javax.persistence.AttributeConverter

@Entity
@Table(name = "bot_detail")
data class BotDetail(
        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        var id: Long? = null,

        @Column
        var title: String? = null,

        @ManyToMany(cascade = [CascadeType.PERSIST]) // CascadeType.Allだとdelete時にgroupまで消されるので
        var lineGroups: List<LineGroup>? = null,

        @ManyToOne(optional = false)
        @NotNull
        var appUser: AppUser? = null,

        @OneToMany(cascade = [CascadeType.ALL], mappedBy = "botDetail", orphanRemoval = true)
        @Nullable
        var botReplyCondition: MutableList<BotReplyCondition>? = mutableListOf(),

        @OneToMany(cascade = [CascadeType.ALL], mappedBy = "botDetail", orphanRemoval = true)
        @Nullable
        var botPushSchedule: MutableList<BotPushSchedule>? = mutableListOf(),

        @Convert(converter = MessageConverter::class)
        var message: Message? = null

): CommonEntity()

class MessageConverter: AttributeConverter<Message, String> {
        // LineSdkで使用しているObjectMapperを使う
        override fun convertToDatabaseColumn(message: Message?): String? {
                if (message != null) {
                        val mapper = ModelObjectMapper.createNewObjectMapper()
                        return mapper.writeValueAsString(message)
                }
                return null
        }
        override fun convertToEntityAttribute(source: String?): Message? {
                if (source != null) {
                        val mapper = ModelObjectMapper.createNewObjectMapper()
                        return mapper.readValue(source, Message::class.java)
                }
                return null
        }
}
