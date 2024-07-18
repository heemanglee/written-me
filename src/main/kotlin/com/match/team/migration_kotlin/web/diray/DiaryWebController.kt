package com.match.team.migration_kotlin.web.diray

import com.match.team.migration_kotlin.domain.user.User
import com.match.team.migration_kotlin.service.diary.DiaryService
import lombok.RequiredArgsConstructor
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
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
        model: Model
    ): String {
        val diaryList = diaryService.getDiaryAll(user)
        model.addAttribute("user", user)
        model.addAttribute("diaryList", diaryList)
        return "diary-list"
    }
}