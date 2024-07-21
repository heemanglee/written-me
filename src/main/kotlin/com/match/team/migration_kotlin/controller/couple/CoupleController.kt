package com.match.team.migration_kotlin.controller.couple

import com.match.team.migration_kotlin.domain.user.User
import com.match.team.migration_kotlin.dto.couple.*
import com.match.team.migration_kotlin.service.couple.CoupleConnectionService
import com.match.team.migration_kotlin.service.couple.CoupleService
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/couples")
@RequiredArgsConstructor
class CoupleController(
    private val coupleService: CoupleService,
    private val coupleConnectionService: CoupleConnectionService
) {

    @PostMapping
    fun createConnectionToken(
        @AuthenticationPrincipal user: User
    ): ResponseEntity<CreateConnectionTokenResponseDto> {
        val connectionToken = coupleConnectionService.createToken(user)
        return ResponseEntity.status(HttpStatus.CREATED).body(connectionToken)
    }

    @GetMapping
    fun getConnectSenderUser(
        @AuthenticationPrincipal user: User,
        @RequestParam(name = "token") request: GetConnectionRequestDto
    ): ResponseEntity<GetConnectionResponseDto> {
        val findSenderId = coupleConnectionService.getSenderId(request.token)
        return ResponseEntity.ok(findSenderId)
    }

    @PatchMapping
    fun createCouple(
        @AuthenticationPrincipal user: User,
        @RequestBody request: CreateCoupleRequestDto
    ): ResponseEntity<CreateCoupleResponseDto> {
        val savedCouple = coupleService.createCouple(user, request)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCouple)
    }
}