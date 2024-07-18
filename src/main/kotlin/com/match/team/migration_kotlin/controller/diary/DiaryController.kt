package com.match.team.migration_kotlin.controller.diary

import com.match.team.migration_kotlin.domain.user.User
import com.match.team.migration_kotlin.dto.diary.CreateDiaryRequestDto
import com.match.team.migration_kotlin.dto.diary.GetDiaryDetailResponseDto
import com.match.team.migration_kotlin.service.diary.DiaryService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/diarys")
class DiaryController(
    private val diaryService: DiaryService
) {

    @PostMapping
    fun createDiary(
        @AuthenticationPrincipal user: User,
        request: CreateDiaryRequestDto
    ): String {
        diaryService.createDiary(user, request)
        return "redirect:/"
    }

    @GetMapping("/{diaryId}")
    fun getDiaryDetail(
        @AuthenticationPrincipal user: User,
        @PathVariable diaryId: Long
    ): ResponseEntity<GetDiaryDetailResponseDto> {
        val diaryDetail = diaryService.getDiaryDetail(diaryId)
        return ResponseEntity.ok(diaryDetail)
    }
}