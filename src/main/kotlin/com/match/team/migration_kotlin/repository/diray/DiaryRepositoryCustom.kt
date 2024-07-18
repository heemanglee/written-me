package com.match.team.migration_kotlin.repository.diray

import com.match.team.migration_kotlin.domain.user.User
import com.match.team.migration_kotlin.dto.diary.GetDiaryDetailResponseDto
import com.match.team.migration_kotlin.dto.diary.GetDiaryResponseDto

interface DiaryRepositoryCustom {

    fun findDiaryAll(user: User): List<GetDiaryResponseDto>

    fun findDiaryDetail(diaryId: Long): GetDiaryDetailResponseDto?
}