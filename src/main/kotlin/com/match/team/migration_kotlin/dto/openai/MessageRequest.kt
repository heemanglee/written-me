package com.match.team.migration_kotlin.dto.openai

data class MessageRequest(
    val model: String,
    val messages: MutableList<Message>,
) {

    companion object {
        fun from(
            model: String,
            inputMessage: String
        ): MessageRequest {
            val list = mutableListOf(Message("user", createPrompt(inputMessage)))
            return MessageRequest(model, list)
        }

        private fun createPrompt(message: String): String {
            val tone = "너는 전문가이고, 상대방이 작성한 일기를 분석하여 긍적적인 말을 작성해야해. 미지막 줄에 한 줄을 띄우고 '요약 : ' 접두어를 붙여 약 10자 이내로 존댓말로 요약해줘."
            val length = "100"
            val style = "동기 부여적인 스타일"
            return """
            ${message} \
            TONE: ${tone} \
            LENGTH: ${length} \
            STYLE: ${style}
        """.trimIndent()
        }
    }


}

data class Message(
    val role: String,
    val content: String
)