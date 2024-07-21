package com.match.team.migration_kotlin.service.couple

import com.match.team.migration_kotlin.domain.couple.Couple
import com.match.team.migration_kotlin.domain.user.User
import com.match.team.migration_kotlin.dto.couple.CreateCoupleRequestDto
import com.match.team.migration_kotlin.dto.couple.CreateCoupleResponseDto
import com.match.team.migration_kotlin.repository.couple.CoupleRepository
import com.match.team.migration_kotlin.repository.user.UserRepository
import com.match.team.migration_kotlin.util.findByIdOrThrow
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
class CoupleService(
    val coupleRepository: CoupleRepository,
    val userRepository: UserRepository
) {

    @Transactional
    fun createCouple(user: User, request: CreateCoupleRequestDto): CreateCoupleResponseDto {
        val sender = userRepository.findByIdOrThrow(request.senderId.toLong())
        val receiver = userRepository.findByIdOrThrow(user.id)
        val savedCouple = coupleRepository.save(Couple.from(sender, receiver))
        return CreateCoupleResponseDto.from(savedCouple)
    }

    @Transactional
    fun disConnectCouple(user: User) {
        val findCoupleDetail = coupleRepository.findCoupleDetail(user.id!!)
            ?: throw IllegalArgumentException("커플 정보를 조회하는데 실패하였습니다. ID : ${user.id}")
        coupleRepository.delete(findCoupleDetail)
    }
}