package com.match.team.migration_kotlin.controller.diary

import com.match.team.migration_kotlin.domain.user.User
import com.match.team.migration_kotlin.dto.diary.CreateDiaryRequestDto
import com.match.team.migration_kotlin.dto.diary.GetDiaryDetailResponseDto
import com.match.team.migration_kotlin.service.diary.DiaryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

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
        val result = diaryService.createDiary(user, request)
        // 일기장 리스트 페이지로 이동함과 동시에 사용자가 작성한 일기 내용 모달을 보여준다.
        return "redirect:/diarys?diaryId=${result.diaryId}"
    }

    @GetMapping("/{diaryId}")
    fun getDiaryDetail(
        @AuthenticationPrincipal user: User,
        @PathVariable diaryId: Long
    ): ResponseEntity<GetDiaryDetailResponseDto> {
        val diaryDetail = diaryService.getDiaryDetail(diaryId)
        return ResponseEntity.ok(diaryDetail)
    }

    @PatchMapping("/{diaryId}/like")
    fun updateDiaryLikeStatus(
        @AuthenticationPrincipal user: User,
        @PathVariable diaryId: Long
    ): ResponseEntity<Unit> {
        diaryService.updateDiaryLikeStatus(diaryId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
    }
}