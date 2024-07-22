package com.match.team.migration_kotlin.repository.diary

import com.match.team.migration_kotlin.domain.user.User
import com.match.team.migration_kotlin.dto.diary.GetDiaryByYearAndMonthResponseDto
import com.match.team.migration_kotlin.dto.diary.GetDiaryDetailResponseDto
import com.match.team.migration_kotlin.dto.diary.GetDiaryResponseDto

interface DiaryRepositoryCustom {

    fun findDiaryAll(user: User, feels: List<String>? = null): List<GetDiaryResponseDto>

    fun findDiaryDetail(diaryId: Long): GetDiaryDetailResponseDto?

    fun findDiaryByMonth(user: User, year:Int, month: Int): List<GetDiaryByYearAndMonthResponseDto>
}