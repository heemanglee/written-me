package com.match.team.migration_kotlin.domain.diary

import com.match.team.migration_kotlin.common.BaseEntity
import com.match.team.migration_kotlin.domain.openai.Message
import com.match.team.migration_kotlin.domain.user.User
import jakarta.persistence.*
import lombok.AccessLevel
import lombok.NoArgsConstructor

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "diarys")
class Diary(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diarys_id")
    val id: Long? = null,

    @Enumerated(EnumType.STRING)
    var feelStatus: FeelStatus,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id")
    val message: Message,

    @Column
    var isLike: Boolean = false,

    var content: String
) : BaseEntity() {

    companion object {
        fun from(
            user: User,
            feelStatus: FeelStatus,
            message: Message,
            content: String
        ): Diary {
            return Diary(
                id = null,
                feelStatus = feelStatus,
                user = user,
                message = message,
                content = content
            )
        }
    }

    fun updateLikeStatus() {
        isLike = !isLike
    }
}