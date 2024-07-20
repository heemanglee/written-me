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

    val content: String,
    val summary: String
) : BaseEntity() {

    companion object {
        fun of(content: String): Message {
            return Message(
                id = null,
                content = createContent(content),
                summary = createSummary(content)
            )
        }

        // 응답 중에 요약본만 추출
        private fun createSummary(content: String): String {
            val index = content.lastIndexOf(":")
            return content.substring(index + 1)
        }

        // 요약을 포함하지 않는 응답
        private fun createContent(content: String): String {
            val index = content.lastIndexOf("요약")
            return content.substring(0, index).trim()
        }
    }
}