package com.match.team.migration_kotlin.web.user

import com.match.team.migration_kotlin.domain.user.User
import lombok.RequiredArgsConstructor
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
class UserWebController {

    @GetMapping("/sign-up")
    fun signupPage(): String {
        return "/sign-up"
    }

    @GetMapping("/sign-in")
    fun loginPage(
    ): String {
        return "sign-in"
    }

    @GetMapping("/profile")
    fun profilePage(@AuthenticationPrincipal user: User,
                    model: Model): String {
        model.addAttribute("user", user)
        println("user=${user.nickName}")
        return "profile"
    }
}