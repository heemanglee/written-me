package com.match.team.migration_kotlin.repository.couple

import com.match.team.migration_kotlin.domain.couple.QCouple.couple
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import lombok.RequiredArgsConstructor

@RequiredArgsConstructor
class CoupleRepositoryCustomImpl(
    private val queryFactory: JPAQueryFactory
) : CoupleRepositoryCustom {

    override fun existsCouple(userId: Long): Boolean {
        val couple = queryFactory
            .select(couple.count())
            .from(couple)
            .where(eqReceiverOrSender(userId))
            .fetch()
        return couple.size > 0
    }

    private fun eqReceiverOrSender(userId: Long): BooleanExpression {
        return couple.receiver.id.eq(userId).or(couple.sender.id.eq(userId))
    }
}