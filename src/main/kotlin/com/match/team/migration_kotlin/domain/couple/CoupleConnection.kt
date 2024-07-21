package com.match.team.migration_kotlin.domain.couple

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive

@RedisHash("CoupleConnection")
class CoupleConnection(
    @Id
    val connectionToken: String, // 연결 토큰

    val userId: String, // 사용자 ID

    @TimeToLive
    val expiration: Long = 60L * 10
) {

    companion object {
        fun createConnection(
            userId: Long,
            connectionToken: String
        ): CoupleConnection {
            return CoupleConnection(
                connectionToken = connectionToken,
                userId = userId.toString()
            )
        }
    }
}