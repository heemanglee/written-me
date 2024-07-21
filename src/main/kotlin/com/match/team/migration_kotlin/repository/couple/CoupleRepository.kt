package com.match.team.migration_kotlin.repository.couple

import com.match.team.migration_kotlin.domain.couple.Couple
import org.springframework.data.jpa.repository.JpaRepository

interface CoupleRepository : JpaRepository<Couple, Long>, CoupleRepositoryCustom {

}