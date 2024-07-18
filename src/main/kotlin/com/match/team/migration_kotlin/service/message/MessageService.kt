package com.match.team.migration_kotlin.service.message

import com.match.team.migration_kotlin.dto.openai.MessageRequest
import com.match.team.migration_kotlin.dto.openai.MessageResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class MessageService(
    @Value("\${openai.model}")
    private val model: String,
    @Value("\${openai.api.url}")
    private val apiUrl: String,
    private val template: RestTemplate
) {

    fun createAiResponse(prompt: String): String {
        val request = MessageRequest.from(model, prompt)
        val response = template.postForObject(apiUrl, request, MessageResponse::class.java)
        return response!!.choices[0].message.content
    }
}