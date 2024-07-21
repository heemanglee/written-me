package com.match.team.migration_kotlin.web.diary

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/calendars")
class CalendarController {

    @GetMapping
    fun showCalendar(): String {
        return "calendar" // templates/calendar.html을 반환
    }
}