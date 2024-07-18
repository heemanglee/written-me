//package com.match.team.migration_kotlin.controller.openai
//
//import com.match.team.migration_kotlin.dto.openai.MessageRequest
//import com.match.team.migration_kotlin.dto.openai.MessageResponse
//import lombok.RequiredArgsConstructor
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.security.core.annotation.AuthenticationPrincipal
//import org.springframework.security.core.userdetails.User
//import org.springframework.web.bind.annotation.GetMapping
//import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.RequestParam
//import org.springframework.web.bind.annotation.RestController
//import org.springframework.web.client.RestTemplate
//
//@RestController
//@RequestMapping("/chats")
//@RequiredArgsConstructor
//class OpenAIChatController(
//) {
//
//    @GetMapping
//    fun sendPrompt(
//        @AuthenticationPrincipal user: User?,
//        @RequestParam prompt: String): String {
//        val request = MessageRequest.from(model, prompt)
//        val response = template.postForObject(apiUrl, request, MessageResponse::class.java)
//        return response!!.choices[0].message.content
//    }
//
//}