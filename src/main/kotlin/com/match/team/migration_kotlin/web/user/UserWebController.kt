package com.match.team.migration_kotlin.web.user

import com.match.team.migration_kotlin.domain.user.User
import com.match.team.migration_kotlin.repository.user.UserRepository
import com.match.team.migration_kotlin.service.user.UserService
import lombok.RequiredArgsConstructor
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
class UserWebController(
    val userRepository: UserRepository,
    private val userService: UserService
) {

    @GetMapping("/sign-up")
    fun signupPage(): String {
        return "sign-up"
    }

    @GetMapping("/sign-in")
    fun loginPage(
    ): String {
        return "sign-in"
    }

    @GetMapping("/profile")
    fun profilePage(@AuthenticationPrincipal user: User,
                    model: Model): String {
        val findUser = userRepository.findUser(user.email, user.password)
        val imageUrl = userService.getUserImageUploadUrl(user)
        model.addAttribute("user", findUser)
        model.addAttribute("imageUrl", imageUrl)
        println("imageUrl = $imageUrl")
        return "profile"
    }
}