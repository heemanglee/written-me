package com.match.team.migration_kotlin.repository.diray

import com.match.team.migration_kotlin.domain.diary.QDiary.diary
import com.match.team.migration_kotlin.domain.user.QUser
import com.match.team.migration_kotlin.domain.user.User
import com.match.team.migration_kotlin.dto.diary.GetDiaryDetailResponseDto
import com.match.team.migration_kotlin.dto.diary.GetDiaryResponseDto
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import lombok.RequiredArgsConstructor

@RequiredArgsConstructor
class DiaryRepositoryCustomImpl(
    private val queryFactory: JPAQueryFactory
) : DiaryRepositoryCustom {

    override fun findDiaryAll(user: User): List<GetDiaryResponseDto> {
        return queryFactory
            .select(
                Projections.constructor(
                    GetDiaryResponseDto::class.java,
                    diary.id,
                    QUser.user.nickName,
                    diary.createdDate,
                    diary.feelStatus
                )
            )
            .from(diary)
            .join(QUser.user).on(diary.user.eq(QUser.user))
            .fetch()
    }

    override fun findDiaryDetail(diaryId: Long): GetDiaryDetailResponseDto? {
        return queryFactory
            .select(
                Projections.constructor(
                    GetDiaryDetailResponseDto::class.java,
                    diary.id, diary.content, diary.feelStatus
                )
            )
            .from(diary)
            .where(diary.id.eq(diaryId))
            .fetchOne()
    }
}
