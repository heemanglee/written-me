package com.match.team.migration_kotlin.dto.openai

data class MessageResponse(
    val choices: List<Choice>
)

data class Choice(
    val index: Int,
    val message: Message
)