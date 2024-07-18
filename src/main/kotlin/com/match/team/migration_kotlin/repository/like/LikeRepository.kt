package com.match.team.migration_kotlin.repository.like

import com.match.team.migration_kotlin.domain.like.Like
import org.springframework.data.jpa.repository.JpaRepository

interface LikeRepository : JpaRepository<Like, Long>