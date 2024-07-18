package com.match.team.migration_kotlin.util

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull

fun <T, Id> CrudRepository<T, Id>.findUserById(id: Id): T {
    return this.findByIdOrNull(id) ?: fail("사용자가 조회되지 않습니다. ID : ${id}")
}

fun <T, Id> CrudRepository<T, Id>.findByIdOrThrow(id: Id): T {
    return this.findByIdOrNull(id) ?: fail("엔티티가 조회되지 않습니다. ID : ${id}")
}