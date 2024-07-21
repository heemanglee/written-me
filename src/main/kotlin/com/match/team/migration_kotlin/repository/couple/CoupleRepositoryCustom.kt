package com.match.team.migration_kotlin.repository.couple

interface CoupleRepositoryCustom {

    fun existsCouple(userId: Long): Boolean
}