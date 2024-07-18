package com.match.team.migration_kotlin.dto.user

data class CreateUserResponseDto(
    val userId: Long,
    val email: String,
    val nickName: String,
    val password: String,
    val age: Int
) {

    companion object {
        fun from(
            userId: Long,
            email: String,
            nickName: String,
            password: String,
            age: Int
        ): CreateUserResponseDto {
            return CreateUserResponseDto(userId, email, nickName, password, age)
        }
    }
}