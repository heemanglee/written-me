package com.match.team.migration_kotlin.domain.like

import com.match.team.migration_kotlin.domain.diary.Diary
import jakarta.persistence.*
import lombok.AccessLevel
import lombok.NoArgsConstructor
import org.springframework.security.core.userdetails.User

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "likes")
class Like(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    val diary: Diary
) {

    companion object {
        fun from(
            diary: Diary
        ): Like {
            return Like(
                id = null,
                diary
            )
        }
    }
}