package com.match.team.migration_kotlin.service.diary

import com.match.team.migration_kotlin.domain.diary.Diary
import com.match.team.migration_kotlin.domain.diary.FeelStatus
import com.match.team.migration_kotlin.domain.diary.Secret
import com.match.team.migration_kotlin.domain.diary.SecretPasswordGenerator
import com.match.team.migration_kotlin.domain.openai.Message
import com.match.team.migration_kotlin.domain.user.User
import com.match.team.migration_kotlin.dto.diary.*
import com.match.team.migration_kotlin.repository.diary.DiaryRepository
import com.match.team.migration_kotlin.repository.diary.SecretRepository
import com.match.team.migration_kotlin.repository.message.MessageRepository
import com.match.team.migration_kotlin.service.message.MessageService
import com.match.team.migration_kotlin.util.fail
import com.match.team.migration_kotlin.util.findByIdOrThrow
import lombok.RequiredArgsConstructor
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
class DiaryService(
    private val diaryRepository: DiaryRepository,
    private val messageService: MessageService,
    private val messageRepository: MessageRepository,
    private val secretPasswordGenerator: SecretPasswordGenerator,
    private val passwordEncoder: PasswordEncoder,
    private val secretRepository: SecretRepository
) {

    @Transactional
    fun createDiary(
        user: User,
        request: CreateDiaryRequestDto
    ): CreateDiaryResponseDto {
        // 생성형 AI 응답 생성
        val createAiResponse = messageService.createAiResponse(request.content)
        val message = Message.of(createAiResponse)
        val savedMessage = messageRepository.save(message)

        // 일기 작성
        val diary =
            Diary.from(user, FeelStatus.valueOf(request.feel), savedMessage, request.content)
        val result = diaryRepository.save(diary)

        return CreateDiaryResponseDto(
            result.id!!,
            result.content,
            result.feelStatus,
            message.content,
            message.summary
        )
    }

    @Transactional(readOnly = true)
    fun getDiaryAll(user: User, feels: List<String>? = null): List<GetDiaryResponseDto> {
        if (feels.isNullOrEmpty())
            return diaryRepository.findDiaryAll(user)
        return diaryRepository.findDiaryAll(user, feels)
    }

    @Transactional(readOnly = true)
    fun getDiaryDetail(diaryId: Long): GetDiaryDetailResponseDto {
        val diaryDetail =
            diaryRepository.findDiaryDetail(diaryId) ?: fail("일기를 조회할 수 없습니다. ID = ${diaryId}")
        return diaryDetail
    }

    @Transactional
    fun updateDiaryLikeStatus(diaryId: Long) {
        val findDiary = diaryRepository.findByIdOrThrow(diaryId)
        findDiary.updateLikeStatus()
    }

    @Transactional(readOnly = true)
    fun findDiaryByMonth(
        user: User,
        year: Int,
        month: Int
    ): List<GetDiaryByYearAndMonthResponseDto> {
        val findDiarys = diaryRepository.findDiaryByMonth(user, year, month)
        return findDiarys
    }

    @Transactional
    fun updateSecretNumber(diaryId: Long, request: DiaryPasswordCreateRequestDto) {
        val findDiary = diaryRepository.findByIdOrThrow(diaryId)

        val encryptPassword = secretPasswordGenerator.createPassword(request.inputNumber)
        if (findDiary.secret != null) {
            findDiary.secret?.updateSecretNumber(encryptPassword)
        } else {
            val savedSecretPassword = secretRepository.save(Secret.of(encryptPassword))
            findDiary.configureSecretPassword(savedSecretPassword)
        }
    }

    @Transactional
    fun deleteSecretNumber(diaryId: Long) {
        val findDiary = diaryRepository.findByIdOrThrow(diaryId)
        findDiary.configureSecretPassword(null)
    }

    @Transactional
    fun findSecretNumber(diaryId: Long): GetDiaryPasswordResponseDto {
        val findDiary = diaryRepository.findDiarySecretNumber(diaryId)
            ?: throw IllegalArgumentException("일기장이 조회되지 않습니다. ID = ${diaryId}")
        return findDiary
    }

    @Transactional(readOnly = true)
    fun isMatchDiaryPassword(diaryId: Long, request: CheckDiaryPasswordRequestDto): Boolean {
        val findDiary =  diaryRepository.findByIdOrThrow(diaryId)
        return passwordEncoder.matches(request.inputPassword, findDiary.secret?.secretNumber)
    }

    @Transactional(readOnly = true)
    fun getFilterCategoryDiarys(user: User, category: String): List<GetDiaryResponseDto> {
        val findDiarys = diaryRepository.findFilterCategory(user, category)
        return findDiarys
    }
}