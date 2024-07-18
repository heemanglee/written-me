package com.match.team.migration_kotlin.repository.thumbnail

import com.match.team.migration_kotlin.domain.thumbnail.Thumbnail
import org.springframework.data.jpa.repository.JpaRepository

interface ThumbnailRepository : JpaRepository<Thumbnail, Long> {

}