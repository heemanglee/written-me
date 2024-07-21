package com.match.team.migration_kotlin.repository.couple

import com.match.team.migration_kotlin.domain.couple.Couple

interface CoupleRepositoryCustom {

    fun existsCouple(userId: Long): Boolean

    fun findCoupleDetail(userId: Long): Couple?
}