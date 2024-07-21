package com.match.team.migration_kotlin.domain.couple

import com.match.team.migration_kotlin.util.TokenGenerator
import org.springframework.stereotype.Component
import java.util.*

@Component
class CoupleConnectionTokenGenerator(
) : TokenGenerator {

    override fun generateToken(): String {
        return UUID.randomUUID().toString()
    }

}