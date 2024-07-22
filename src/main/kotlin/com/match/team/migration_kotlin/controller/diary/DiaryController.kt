package com.match.team.migration_kotlin.controller.diary

import com.match.team.migration_kotlin.domain.user.User
import com.match.team.migration_kotlin.dto.diary.*
import com.match.team.migration_kotlin.service.diary.DiaryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/api/diarys")
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

    @GetMapping
    fun filterDiary(
        @AuthenticationPrincipal user: User,
        @RequestParam(required = false) feels: List<String>?
    ): ResponseEntity<List<GetDiaryResponseDto>> {
        val result = diaryService.getDiaryAll(user, feels)
        return ResponseEntity.ok(result)
    }

    @GetMapping("/{year}/{month}/date")
    fun getDiaryByDate(
        @AuthenticationPrincipal user: User,
        @PathVariable("year") year: Int,
        @PathVariable("month") month: Int,
    ): ResponseEntity<List<GetDiaryByYearAndMonthResponseDto>> {
        val diarys = diaryService.findDiaryByMonth(user, year, month)
        return ResponseEntity.ok(diarys)
    }

    @PatchMapping("/{diaryId}/password")
    fun configurePassword(
        @AuthenticationPrincipal user: User,
        @PathVariable("diaryId") diaryId: Long,
        @RequestBody request: DiaryPasswordCreateRequestDto
    ): ResponseEntity<Unit> {
        diaryService.updateSecretNumber(diaryId, request)
        return ResponseEntity.status(HttpStatus.CREATED).body(null)
    }

    @DeleteMapping("/{diaryId}/password")
    fun deletePassword(
        @AuthenticationPrincipal user: User,
        @PathVariable("diaryId") diaryId: Long,
    ): ResponseEntity<Unit> {
        diaryService.deleteSecretNumber(diaryId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
    }

    @GetMapping("/{diaryId}/password")
    fun getDiaryPassword(
        @AuthenticationPrincipal user: User,
        @PathVariable("diaryId") diaryId: Long
    ): ResponseEntity<GetDiaryPasswordResponseDto> {
        val result = diaryService.findSecretNumber(diaryId)
        return ResponseEntity.ok(result)
    }

    @PostMapping("/{diaryId}/password")
    fun matchPassword(
        @AuthenticationPrincipal user: User,
        @PathVariable("diaryId") diaryId: Long,
        @RequestBody request: CheckDiaryPasswordRequestDto
    ): ResponseEntity<Boolean> {
        val result = diaryService.isMatchDiaryPassword(diaryId, request)
        return ResponseEntity.ok(result)
    }

}