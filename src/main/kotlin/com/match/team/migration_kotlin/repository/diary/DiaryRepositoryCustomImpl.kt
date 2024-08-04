package com.match.team.migration_kotlin.repository.diary

import com.match.team.migration_kotlin.domain.couple.QCouple.couple
import com.match.team.migration_kotlin.domain.diary.FeelStatus
import com.match.team.migration_kotlin.domain.diary.QDiary.diary
import com.match.team.migration_kotlin.domain.diary.QSecret.secret
import com.match.team.migration_kotlin.domain.file.QUploadFile.uploadFile
import com.match.team.migration_kotlin.domain.openai.QMessage.message
import com.match.team.migration_kotlin.domain.user.QUser
import com.match.team.migration_kotlin.domain.user.User
import com.match.team.migration_kotlin.dto.diary.GetDiaryByYearAndMonthResponseDto
import com.match.team.migration_kotlin.dto.diary.GetDiaryDetailResponseDto
import com.match.team.migration_kotlin.dto.diary.GetDiaryPasswordResponseDto
import com.match.team.migration_kotlin.dto.diary.GetDiaryResponseDto
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.JPQLQuery
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
                    message.summary,
                    uploadFile.uploadFileName
                )
            )
            .from(diary)
            .join(message).on(diary.message.id.eq(message.id)).fetchJoin()
            .join(QUser.user).on(diary.user.eq(QUser.user)).fetchJoin()
            .leftJoin(uploadFile).on(uploadFile.eq(QUser.user.profileImage)).fetchJoin()
            .leftJoin(couple).on(couple.sender.eq(user).or(couple.receiver.eq(user))).fetchJoin()
            .where(filterByFeels(feels), isUserOrCoupleDiary(user))
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

    override fun findDiaryByMonth(
        user: User,
        year: Int,
        month: Int
    ): List<GetDiaryByYearAndMonthResponseDto> {
        return queryFactory
            .select(
                Projections.constructor(
                    GetDiaryByYearAndMonthResponseDto::class.java,
                    diary.id, diary.content, diary.feelStatus,
                    diary.message.content, diary.createdDate.dayOfMonth()
                )
            )
            .from(diary)
            .where(eqYearAndMonth(year, month))
            .fetch()
    }

    override fun findDiarySecretNumber(diaryId: Long): GetDiaryPasswordResponseDto? {
        return queryFactory
            .select(
                Projections.constructor(
                    GetDiaryPasswordResponseDto::class.java,
                    diary.id, secret.secretNumber
                )
            )
            .from(diary)
            .leftJoin(secret).on(diary.secret.eq(secret))
            .where(diary.id.eq(diaryId))
            .fetchOne()
    }

    override fun isMatchDiaryPassword(diaryId: Long, password: String): Boolean {
        val findDiary = queryFactory
            .selectFrom(diary)
            .leftJoin(secret).on(diary.secret.id.eq(secret.id))
            .where(secret.secretNumber.eq(password))
            .fetchOne()
        return findDiary != null
    }

    override fun findFilterCategory(user: User, category: String): List<GetDiaryResponseDto> {
        return queryFactory
            .select(
                Projections.constructor(
                    GetDiaryResponseDto::class.java,
                    diary.id,
                    QUser.user.nickName,
                    diary.createdDate,
                    diary.feelStatus,
                    diary.isLike,
                    message.summary,
                    uploadFile.uploadFileName
                )
            )
            .from(diary)
            .join(message).on(diary.message.eq(message)).fetchJoin()
            .join(QUser.user).on(diary.user.eq(QUser.user)).fetchJoin()
            .leftJoin(uploadFile).on(uploadFile.eq(QUser.user.profileImage)).fetchJoin()
            .leftJoin(couple).on(couple.sender.eq(user).or(couple.receiver.eq(user))).fetchJoin()
            .where(filterCategory(user, category))
            .fetch()
    }

    private fun filterByFeels(feels: List<String>?): BooleanExpression? {
        return feels?.let {
            diary.feelStatus.`in`(feels.map { FeelStatus.valueOf(it) })
        }
    }

    private fun eqYearAndMonth(year: Int, month: Int): BooleanExpression? {
        return diary.createdDate.year().eq(year).and(diary.createdDate.month().eq(month + 1))
    }

    private fun filterCategory(user: User, category: String): BooleanExpression? {
        return when(category) {
            "all" -> isUserOrCoupleDiary(user)
            "personal" -> diary.user.eq(user)
            else -> diary.user.id.eq(couple.sender.id).and(couple.receiver.id.eq(user.id))
                .or(diary.user.id.eq(couple.receiver.id).and(couple.sender.id.eq(user.id)))
        }
    }

    private fun isUserOrCoupleDiary(user: User): BooleanExpression {
        val coupleDiary = couple

        val userIds: JPQLQuery<Long> = JPAExpressions
            .select(
                coupleDiary.sender.id
                    .`when`(user.id).then(coupleDiary.receiver.id)
                    .`otherwise`(coupleDiary.sender.id)
            )
            .from(coupleDiary)
            .where(coupleDiary.sender.eq(user).or(coupleDiary.receiver.eq(user)))

        return diary.user.id.eq(user.id)
            .or(diary.user.id.`in`(userIds))
    }

}
