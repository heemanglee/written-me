package com.match.team.migration_kotlin.repository.user

import com.match.team.migration_kotlin.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>, UserRepositoryCustom {

    fun existsByEmail(email: String): Boolean

    fun findUserByEmail(email: String): User?

    fun findByEmailAndPassword(email: String, password: String): User?
}