package com.match.team.migration_kotlin.domain.openai

import com.match.team.migration_kotlin.common.BaseEntity
import jakarta.persistence.*
import lombok.AccessLevel
import lombok.NoArgsConstructor

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "messages")
class Message(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    val id: Long? = null,

    val content: String
) : BaseEntity() {

    companion object {
        fun of(content: String): Message {
            return Message(
                id = null,
                content = content
            )
        }
    }
}