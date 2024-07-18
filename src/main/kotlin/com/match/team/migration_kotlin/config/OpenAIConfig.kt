package com.match.team.migration_kotlin.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class OpenAIConfig(
    @Value("\${openai.api.key}")
    private val openAiKey: String
) {

    @Bean
    fun template(): RestTemplate {
        val restTemplate = RestTemplate()
        restTemplate.interceptors.add { request, body, execution ->
            request.headers.add("Authorization", "Bearer ${openAiKey}")
            execution.execute(request, body)
        }
        return restTemplate
    }
}