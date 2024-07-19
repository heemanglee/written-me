package com.match.team.migration_kotlin.dto.user

data class GetUserResponseDto(
    val userId: Long,
    val email: String,
    val age: Int,
    val nickName: String,
    val password: String,
    val uploadImageName: String?
) {

    companion object {
        fun from(
            userId: Long,
            email: String,
            age: Int,
            nickName: String,
            password: String,
            uploadImageName: String?
        ): GetUserResponseDto {
            return GetUserResponseDto(userId, email, age, nickName, password, uploadImageName ?: "default_user_img.png")
        }
    }
}