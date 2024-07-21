package com.match.team.migration_kotlin.domain.couple

import com.match.team.migration_kotlin.domain.user.User
import jakarta.persistence.*
import lombok.AccessLevel
import lombok.NoArgsConstructor

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "couples")
class Couple(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "couple_id")
    val id: Long? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    val sender: User,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    val receiver: User
) {

    companion object {
        fun from(sender: User, receiver: User): Couple {
            return Couple(
                sender = sender,
                receiver = receiver
            )
        }
    }
}