package com.match.team.migration_kotlin.repository.message

import com.match.team.migration_kotlin.domain.openai.Message
import org.springframework.data.jpa.repository.JpaRepository

interface MessageRepository : JpaRepository<Message, Long>