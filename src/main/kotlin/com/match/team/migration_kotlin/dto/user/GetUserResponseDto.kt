package com.match.team.migration_kotlin.dto.user

data class GetUserResponseDto(
    val userId: Long,
    val email: String,
    val nickName: String,
    val password: String,
) {

    companion object {
        fun from(
            userId: Long,
            email: String,
            nickName: String,
            password: String
        ): GetUserResponseDto {
            return GetUserResponseDto(userId, email, nickName, password)
        }
    }
}