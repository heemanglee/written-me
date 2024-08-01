package com.match.team.migration_kotlin.controller

import com.match.team.migration_kotlin.domain.user.User
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController {

    @GetMapping("/")
    fun home(@AuthenticationPrincipal user: User?,
             model: Model): String {
        model.addAttribute("user", user)
        return "main"
    }

    @GetMapping("/health")
    fun health(): String {
        return "Success Health Check"
    }
}