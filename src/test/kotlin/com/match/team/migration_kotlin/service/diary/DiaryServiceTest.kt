package com.match.team.migration_kotlin.service.diary

import com.match.team.migration_kotlin.domain.diary.Diary
import com.match.team.migration_kotlin.domain.diary.FeelStatus
import com.match.team.migration_kotlin.domain.user.Role
import com.match.team.migration_kotlin.domain.user.User
import com.match.team.migration_kotlin.dto.diary.CreateDiaryRequestDto
import com.match.team.migration_kotlin.dto.diary.GetDiaryResponseDto
import com.match.team.migration_kotlin.repository.diary.DiaryRepository
import com.match.team.migration_kotlin.repository.message.MessageRepository
import com.match.team.migration_kotlin.service.message.MessageService
import com.match.team.migration_kotlin.util.createDiary
import com.match.team.migration_kotlin.util.createMessage
import com.match.team.migration_kotlin.util.createUser
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import org.springframework.test.util.ReflectionTestUtils
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class DiaryServiceTest {

    @Mock
    lateinit var diaryRepository: DiaryRepository

    @Mock
    lateinit var messageService: MessageService

    @Mock
    lateinit var messageRepository: MessageRepository

    @InjectMocks
    lateinit var diaryService: DiaryService

    @Test
    @DisplayName("사용자가 작성한 모든 일기를 조회할 수 있다.")
    fun getDiaryAllTest() {
        // given
        var user1 = createUser()
        var diary1 = createDiary(user1)
        ReflectionTestUtils.setField(diary1, "id", 1L)

        var user2 = User(
            email = "test@test.com",
            nickName = "test",
            role = Role.USER,
            age = 20,
            password = "test"
        )
        var diary2 = Diary(
            feelStatus = FeelStatus.BAD,
            user = user2,
            content = "diary2",
            message = createMessage()
        )
        val now = LocalDateTime.now()
        ReflectionTestUtils.setField(diary1, "createdDate", now)
        ReflectionTestUtils.setField(diary2, "createdDate", now)

        val responseDto = GetDiaryResponseDto(
            diaryId = diary1.id!!,
            nickName = user1.nickName,
            diaryDate = now,
            feel = diary1.feelStatus
        )
        whenever(diaryRepository.findDiaryAll(user1)).thenReturn(listOf(responseDto))

        // when
        val diaryList = diaryService.getDiaryAll(user1)

        // then
        assertThat(diaryList).isNotEmpty
        assertThat(diaryList).hasSize(1)
        assertThat(diaryList).hasSize(1)
        assertThat(diaryList[0].diaryDate).isEqualTo(now)
        assertThat(diaryList[0].nickName).isEqualTo(user1.nickName)
        assertThat(diaryList[0].nickName).isNotEqualTo(user2.nickName)
        assertThat(diaryList[0].feel).isEqualTo(diary1.feelStatus)
        assertThat(diaryList[0].feel).isNotEqualTo(diary2.feelStatus)
    }

    @Test
    @DisplayName("사용자는 일기를 작성할 수 있다.")
    fun createDiaryTest() {
        // given
        val user = createUser()
        val diary = createDiary(user)
        val message = createMessage()

        val requestDto = CreateDiaryRequestDto(
            feel = diary.feelStatus.toString(),
            content = diary.content
        )

        whenever(messageService.createAiResponse(requestDto.content)).thenReturn(message.content)

        val savedDiary = Diary(
            feelStatus = diary.feelStatus,
            user = user,
            content = diary.content,
            message = message
        )
        ReflectionTestUtils.setField(savedDiary, "id", 1L)


        whenever(messageRepository.save(any())).thenReturn(message)
        whenever(diaryRepository.save(any())).thenReturn(savedDiary)

        // when
        val result = diaryService.createDiary(user, requestDto)

        // then
        assertThat(result).isNotNull
        assertThat(result.diaryId).isEqualTo(savedDiary.id)
        assertThat(result.content).isEqualTo(savedDiary.content)
        assertThat(result.feel).isEqualTo(savedDiary.feelStatus)
        assertThat(result.aiResponse).isEqualTo(message.content)
    }
}