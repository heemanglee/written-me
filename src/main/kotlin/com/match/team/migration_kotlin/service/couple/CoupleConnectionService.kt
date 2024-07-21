package com.match.team.migration_kotlin.service.couple

import com.match.team.migration_kotlin.domain.couple.CoupleConnection
import com.match.team.migration_kotlin.domain.user.User
import com.match.team.migration_kotlin.dto.couple.CreateConnectionTokenResponseDto
import com.match.team.migration_kotlin.dto.couple.GetConnectionResponseDto
import com.match.team.migration_kotlin.repository.couple.CoupleConnectionRepository
import com.match.team.migration_kotlin.repository.couple.CoupleRepository
import com.match.team.migration_kotlin.util.TokenGenerator
import com.match.team.migration_kotlin.util.findByIdOrThrow
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class CoupleConnectionService(
    val coupleConnectionRepository: CoupleConnectionRepository,
    val tokenGenerator: TokenGenerator,
    val coupleRepository: CoupleRepository
) {

    fun createToken(user: User): CreateConnectionTokenResponseDto {
        validateIsCouple(user.id!!)
        val token = tokenGenerator.generateToken() // 연결을 위한 토큰 생성
        val createConnection = CoupleConnection.createConnection(user.id!!, token) // 연결 생성
        coupleConnectionRepository.save(createConnection)

        return CreateConnectionTokenResponseDto(token)
    }

    fun getSenderId(
        token: String
    ): GetConnectionResponseDto {
        val findToken = extractToken(token)
        println("token=${findToken}, ${token}")
        val findConnection = coupleConnectionRepository.findByIdOrThrow(findToken)
        return GetConnectionResponseDto(findConnection.userId.toLong())
    }

    private fun extractToken(token: String): String {
        val index = token.indexOf("CoupleConnection:")
        return token.substring(index + 1)
    }

    private fun validateIsCouple(userId: Long) {
        if (coupleRepository.existsCouple(userId)) {
            throw IllegalArgumentException("두 명 이상의 연인을 등록할 수 없습니다. ID : ${userId}")
        }
    }
}