package com.match.team.migration_kotlin.util

fun fail(message: String?): Nothing {
    throw IllegalArgumentException(message)
}