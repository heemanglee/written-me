package com.match.team.migration_kotlin.dto.couple

import com.match.team.migration_kotlin.domain.couple.Couple

data class CreateCoupleResponseDto(
    val coupleId: Long,
    val senderId: Long,
    val receiverId: Long
) {

    companion object {
        fun from(couple: Couple): CreateCoupleResponseDto {
            return CreateCoupleResponseDto(
                coupleId = couple.id!!,
                senderId = couple.sender.id!!,
                receiverId = couple.receiver.id!!
            )
        }
    }
}