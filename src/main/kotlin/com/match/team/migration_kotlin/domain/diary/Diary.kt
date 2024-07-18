package com.match.team.migration_kotlin.domain.diary

import com.match.team.migration_kotlin.common.BaseEntity
import com.match.team.migration_kotlin.domain.user.User
import jakarta.persistence.*
import lombok.AccessLevel
import lombok.NoArgsConstructor

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class Diary(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    val id: Long? = null,

    @Enumerated(EnumType.STRING)
    var feelStatus: FeelStatus,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    var content: String
) : BaseEntity() {

    companion object {
        fun from(
            user: User,
            feelStatus: FeelStatus,
            content: String
        ): Diary {
            return Diary(
                id = null,
                feelStatus = feelStatus,
                user = user,
                content = content
            )
        }
    }
}