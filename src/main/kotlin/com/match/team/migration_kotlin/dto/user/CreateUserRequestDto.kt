package com.match.team.migration_kotlin.dto.user

data class CreateUserRequestDto(
    val email: String,
    val nickName: String,
    val password: String,
    val age: Int
)