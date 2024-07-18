package com.match.team.migration_kotlin.repository.user

import com.match.team.migration_kotlin.dto.user.GetUserResponseDto

interface UserRepositoryCustom {

    fun findUser(email: String, password: String): GetUserResponseDto?
}