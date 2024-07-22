package com.match.team.migration_kotlin.dto.diary

data class GetDiaryPasswordResponseDto (
    val diaryId: Long,
    val secretNumber: String?
)