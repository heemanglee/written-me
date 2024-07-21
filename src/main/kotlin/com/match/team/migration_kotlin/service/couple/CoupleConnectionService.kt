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

        // 이미 사용자에게 발생한 토큰이 있다면 해당 토큰을 반환한다.
        val isPreviousGeneratorToken = isPreviousGeneratorTokenToUser(user)
        if(isPreviousGeneratorToken != null) {
            return CreateConnectionTokenResponseDto(isPreviousGeneratorToken.connectionToken)
        }

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

    fun isPreviousGeneratorTokenToUser(user: User): CoupleConnection? {
        val coupleConnection = coupleConnectionRepository.findByUserId(user.id.toString())
        return coupleConnection
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