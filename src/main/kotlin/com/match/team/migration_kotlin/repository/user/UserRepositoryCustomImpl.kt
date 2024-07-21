package com.match.team.migration_kotlin.repository.user

import com.match.team.migration_kotlin.domain.couple.QCouple.couple
import com.match.team.migration_kotlin.domain.file.QUploadFile.uploadFile
import com.match.team.migration_kotlin.domain.user.QUser.user
import com.match.team.migration_kotlin.dto.user.GetUserResponseDto
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.CaseBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import lombok.RequiredArgsConstructor


@RequiredArgsConstructor
class UserRepositoryCustomImpl(
    private val queryFactory: JPAQueryFactory
) : UserRepositoryCustom {

    override fun findUser(email: String, password: String): GetUserResponseDto? {
        return queryFactory
            .select(
                Projections.constructor(
                    GetUserResponseDto::class.java,
                    user.id,
                    user.email,
                    user.age,
                    user.nickName,
                    user.password,
                    CaseBuilder()
                        .`when`(user.profileImage.id.isNotNull)
                        .then(uploadFile.uploadFileName)
                        .otherwise(null as String?),
                    CaseBuilder()
                        .`when`(couple.isNotNull)
                        .then(
                            CaseBuilder()
                                .`when`(couple.sender.id.eq(user.id))
                                .then(couple.receiver.nickName)
                                .otherwise(couple.sender.nickName)
                        )
                        .otherwise(null as String?)
                )
            )
            .from(user)
            .leftJoin(user.profileImage, uploadFile).on(user.profileImage.id.eq(uploadFile.id))
            .leftJoin(couple).on(couple.sender.id.eq(user.id).or(couple.receiver.id.eq(user.id)))
            .where(user.email.eq(email), user.password.eq(password))
            .fetchOne()
    }
}