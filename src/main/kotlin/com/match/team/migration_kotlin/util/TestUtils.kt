package com.match.team.migration_kotlin.util

import com.match.team.migration_kotlin.domain.diary.Diary
import com.match.team.migration_kotlin.domain.diary.FeelStatus
import com.match.team.migration_kotlin.domain.user.Role
import com.match.team.migration_kotlin.domain.user.User

fun createUser(): User {
    return User(
        id = null,
        email = "test@test.com",
        role = Role.USER,
        nickName = "nickName",
        password = "password",
        age = 99
    )
}

fun createDiary(user: User): Diary {
    return Diary.from(user, FeelStatus.HAPPY, "content")
}