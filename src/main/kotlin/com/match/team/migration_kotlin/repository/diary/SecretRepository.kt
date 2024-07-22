package com.match.team.migration_kotlin.repository.diary

import com.match.team.migration_kotlin.domain.diary.Secret
import org.springframework.data.jpa.repository.JpaRepository

interface SecretRepository : JpaRepository<Secret, Long> {
}