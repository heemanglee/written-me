package com.match.team.migration_kotlin.dto.diary

import com.match.team.migration_kotlin.domain.diary.FeelStatus
import java.time.LocalDateTime

data class GetDiaryResponseDto(
    val diaryId: Long,
    val nickName: String,
    val diaryDate: LocalDateTime,
    val feel: FeelStatus,
    val likeStatus: Boolean,
    val aiResponseSummary: String,
    val imagePath: String?
)