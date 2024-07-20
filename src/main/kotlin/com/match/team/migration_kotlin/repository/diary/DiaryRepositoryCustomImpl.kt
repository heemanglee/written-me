package com.match.team.migration_kotlin.repository.diary

import com.match.team.migration_kotlin.domain.diary.FeelStatus
import com.match.team.migration_kotlin.domain.diary.QDiary.diary
import com.match.team.migration_kotlin.domain.openai.QMessage
import com.match.team.migration_kotlin.domain.openai.QMessage.message
import com.match.team.migration_kotlin.domain.user.QUser
import com.match.team.migration_kotlin.domain.user.User
import com.match.team.migration_kotlin.dto.diary.GetDiaryDetailResponseDto
import com.match.team.migration_kotlin.dto.diary.GetDiaryResponseDto
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import lombok.RequiredArgsConstructor

@RequiredArgsConstructor
class DiaryRepositoryCustomImpl(
    private val queryFactory: JPAQueryFactory
) : DiaryRepositoryCustom {

    override fun findDiaryAll(user: User, feels: List<String>?): List<GetDiaryResponseDto> {
        return queryFactory
            .select(
                Projections.constructor(
                    GetDiaryResponseDto::class.java,
                    diary.id,
                    QUser.user.nickName,
                    diary.createdDate,
                    diary.feelStatus,
                    diary.isLike,
                    message.summary
                )
            )
            .from(diary)
            .join(message).on(diary.message.id.eq(message.id))
            .join(QUser.user).on(diary.user.eq(QUser.user))
            .where(filterByFeels(feels), eqUser(user))
            .fetch()
    }

    override fun findDiaryDetail(diaryId: Long): GetDiaryDetailResponseDto? {
        return queryFactory
            .select(
                Projections.constructor(
                    GetDiaryDetailResponseDto::class.java,
                    diary.id, diary.content, diary.feelStatus,
                    diary.message.content
                )
            )
            .from(diary)
            .join(message).on(diary.message.eq(message))
            .where(diary.id.eq(diaryId))
            .fetchOne()
    }

    private fun filterByFeels(feels: List<String>?): BooleanExpression? {
        return feels?.let {
            diary.feelStatus.`in`(feels.map { FeelStatus.valueOf(it) })
        }
    }

    private fun eqUser(user: User): BooleanExpression {
        return diary.user.eq(user)
    }

}
