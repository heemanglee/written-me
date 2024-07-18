package com.match.team.migration_kotlin.dto.diary

import com.match.team.migration_kotlin.domain.diary.FeelStatus

data class GetDiaryDetailResponseDto (
    val diaryId: Long,
    val content: String,
    val feel: FeelStatus
)