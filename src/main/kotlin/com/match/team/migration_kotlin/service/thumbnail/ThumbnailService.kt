package com.match.team.migration_kotlin.service.thumbnail

import com.match.team.migration_kotlin.domain.thumbnail.ThumbnailStore
import com.match.team.migration_kotlin.domain.user.User
import com.match.team.migration_kotlin.repository.thumbnail.ThumbnailRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Service
@RequiredArgsConstructor
class ThumbnailService(
    val thumbnailRepository: ThumbnailRepository,
    val thumbnailStore: ThumbnailStore
) {

    @Transactional
    fun updateThumbnail(
        user: User,
        multipartFile: MultipartFile
    ) {

    }
}