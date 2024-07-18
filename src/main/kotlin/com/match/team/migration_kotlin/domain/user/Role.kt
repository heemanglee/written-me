package com.match.team.migration_kotlin.domain.user

import lombok.RequiredArgsConstructor

@RequiredArgsConstructor
enum class Role(
    val key: String,
    val title: String
) {

    USER("ROLE_USER", "사용자"),
    ADMIN("ROLE_ADMIN", "관리자")
}
