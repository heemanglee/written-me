package com.match.team.migration_kotlin.dto.diary

import com.match.team.migration_kotlin.domain.diary.FeelStatus

data class GetDiaryByYearAndMonthResponseDto(
    val diaryId: Long,
    val content: String,
    val feel: FeelStatus,
    val aiResponse: String,
    val day: Int
)