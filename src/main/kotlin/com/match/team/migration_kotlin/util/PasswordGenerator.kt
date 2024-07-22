package com.match.team.migration_kotlin.util

interface PasswordGenerator {

    fun createPassword(inputPassword: String): String
}