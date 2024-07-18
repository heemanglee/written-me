package com.match.team.migration_kotlin.domain.diary

enum class FeelStatus(
    val feel: String
) {
    HAPPY("행복"),
    GOOD("좋음"),
    NOT_BAD("보통"),
    BAD("나쁨"),
    ANGRY("화남")
}