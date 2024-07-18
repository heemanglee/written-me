package com.match.team.migration_kotlin.controller.user

import com.match.team.migration_kotlin.domain.user.User
import com.match.team.migration_kotlin.dto.user.*
import com.match.team.migration_kotlin.service.user.UserService
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
class UserController(
    private val userService: UserService,
) {

    @PostMapping("/sign-up")
    fun saveUser(
        @ModelAttribute request: CreateUserRequestDto
    ): String {
        userService.createUser(request)
        return "redirect:/"
    }

    @GetMapping("/sign-up/check-email/{email}")
    fun checkDuplicateEmail(
        @PathVariable email: String
    ): ResponseEntity<Boolean> {
        val result = userService.checkDuplicateEmail(email)
        return ResponseEntity.ok(result)
    }

    @GetMapping("/sign-up/check-password")
    fun confirmPassword(
        @RequestParam password: String,
        @RequestParam confirmPassword: String
    ): ResponseEntity<Boolean> {
        val result = userService.checkSamePassword(password, confirmPassword)
        return ResponseEntity.ok(result)
    }

    @PostMapping("/profile/nickName")
    fun checkMySelf(
        @AuthenticationPrincipal user: User,
        @RequestBody request: UpdateNickNameRequestDto
    ): ResponseEntity<Boolean> {
        return ResponseEntity.ok(userService.checkMySelf(user, request))
    }

    @PostMapping("/sign-in")
    fun loginUser(
        @ModelAttribute request: LoginUserRequestDto
    ) {
        userService.loginUser(request)
    }

    @PostMapping("/profile/password")
    fun checkEqualsPassword(
        @AuthenticationPrincipal user: User,
        @RequestBody request: CheckEqualsPasswordRequestDto
    ): ResponseEntity<Boolean> {
        return ResponseEntity.ok(userService.samePassword(user, request))
    }

    @PatchMapping("/profile/password")
    fun updatePassword(
        @AuthenticationPrincipal user: User,
        @RequestBody request: UpdatePasswordRequestDto
    ): ResponseEntity<Unit> {
        userService.updatePassword(user, request)
        return ResponseEntity.ok(null)
    }

    @PatchMapping("/profile/thumbnail")
    fun updateThumbnail(
        @AuthenticationPrincipal user: User,
        @RequestParam("file") multipartFile: MultipartFile
    ) {

    }

}