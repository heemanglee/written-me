package com.match.team.migration_kotlin.service.like

import com.match.team.migration_kotlin.domain.like.Like
import com.match.team.migration_kotlin.dto.like.CreateLikeResponseDto
import com.match.team.migration_kotlin.repository.diary.DiaryRepository
import com.match.team.migration_kotlin.repository.like.LikeRepository
import com.match.team.migration_kotlin.util.findByIdOrThrow
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
class LikeService(
    private val likeRepository: LikeRepository,
    private val diaryRepository: DiaryRepository
) {

    @Transactional
    fun clickLike(
        diaryId: Long
    ): CreateLikeResponseDto {
        val diary = diaryRepository.findByIdOrThrow(diaryId)
        val like = Like.from(diary)
        val savedLike = likeRepository.save(like)
        return CreateLikeResponseDto(savedLike.id!!, savedLike.diary.id!!)
    }

    @Transactional
    fun cancelLike(
        likeId: Long
    ) {
        val findLike = likeRepository.findByIdOrThrow(likeId)
        likeRepository.delete(findLike)
    }
}