package com.match.team.migration_kotlin.web.diary

import com.match.team.migration_kotlin.domain.user.User
import com.match.team.migration_kotlin.service.diary.DiaryService
import lombok.RequiredArgsConstructor
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDate

@Controller
@RequiredArgsConstructor
@RequestMapping("/diarys")
class DiaryWebController(
    val diaryService: DiaryService
) {

    @GetMapping("/form")
    fun diaryFormPage(
        model: Model
    ): String {
        model.addAttribute("date", LocalDate.now())
        return "diary-form"
    }

    @GetMapping
    fun diaryListPage(
        @AuthenticationPrincipal user: User,
        @RequestParam(required = false) feels: List<String>?,
        model: Model
    ): String {
        val diaryList = diaryService.getDiaryAll(user, feels)
        model.addAttribute("user", user)
        model.addAttribute("diaryList", diaryList)
        println(user.profileImage?.uploadFileName)
        return "diary-list"
    }
}