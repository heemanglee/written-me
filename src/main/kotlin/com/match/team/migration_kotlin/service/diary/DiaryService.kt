package com.match.team.migration_kotlin.service.diary

import com.match.team.migration_kotlin.domain.diary.Diary
import com.match.team.migration_kotlin.domain.diary.FeelStatus
import com.match.team.migration_kotlin.domain.user.User
import com.match.team.migration_kotlin.dto.diary.CreateDiaryRequestDto
import com.match.team.migration_kotlin.dto.diary.CreateDiaryResponseDto
import com.match.team.migration_kotlin.dto.diary.GetDiaryDetailResponseDto
import com.match.team.migration_kotlin.dto.diary.GetDiaryResponseDto
import com.match.team.migration_kotlin.repository.diray.DiaryRepository
import com.match.team.migration_kotlin.util.fail
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
class DiaryService(
    private val diaryRepository: DiaryRepository
) {

    @Transactional
    fun createDiary(
        user: User,
        request: CreateDiaryRequestDto
    ): CreateDiaryResponseDto {
        val diary = Diary.from(user, FeelStatus.valueOf(request.feel), request.content)
        val result = diaryRepository.save(diary)
        return CreateDiaryResponseDto(result.id!!, result.content, result.feelStatus)
    }

    @Transactional(readOnly = true)
    fun getDiaryAll(user: User): List<GetDiaryResponseDto> {
        return diaryRepository.findDiaryAll(user)
    }

    @Transactional(readOnly = true)
    fun getDiaryDetail(diaryId: Long): GetDiaryDetailResponseDto {
        val diaryDetail =
            diaryRepository.findDiaryDetail(diaryId) ?: fail("일기를 조회할 수 없습니다. ID = ${diaryId}")
        return diaryDetail
    }
}