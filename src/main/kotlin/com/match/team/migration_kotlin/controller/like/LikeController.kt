package com.match.team.migration_kotlin.controller.like

import com.match.team.migration_kotlin.domain.user.User
import com.match.team.migration_kotlin.dto.like.CreateLikeResponseDto
import com.match.team.migration_kotlin.service.like.LikeService
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
class LikeController(
    private val likeService: LikeService
) {

    @PatchMapping("/{likeId}")
    fun clickDiaryLike(
        @AuthenticationPrincipal user: User,
        @PathVariable likeId: Long
    ): ResponseEntity<CreateLikeResponseDto> {
        val response = likeService.clickLike(likeId)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @DeleteMapping("/{likeId}")
    fun cancelDiaryLike(
        @AuthenticationPrincipal user: User,
        @PathVariable likeId: Long
    ): ResponseEntity<Unit> {
        likeService.cancelLike(likeId)
        return ResponseEntity.ok(null)
    }
}