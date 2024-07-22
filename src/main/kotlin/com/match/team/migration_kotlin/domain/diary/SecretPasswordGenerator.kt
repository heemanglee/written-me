package com.match.team.migration_kotlin.domain.diary

import com.match.team.migration_kotlin.util.PasswordGenerator
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class SecretPasswordGenerator(
    private val passwordEncoder: PasswordEncoder,
) : PasswordGenerator {

    override fun createPassword(inputPassword: String): String {
        return passwordEncoder.encode(inputPassword)
    }
}