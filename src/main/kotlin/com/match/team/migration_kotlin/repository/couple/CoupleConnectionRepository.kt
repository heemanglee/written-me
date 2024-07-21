package com.match.team.migration_kotlin.repository.couple

import com.match.team.migration_kotlin.domain.couple.CoupleConnection
import org.springframework.data.repository.CrudRepository

interface CoupleConnectionRepository : CrudRepository<CoupleConnection, String> {
}