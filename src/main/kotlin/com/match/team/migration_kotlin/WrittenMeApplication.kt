package com.match.team.migration_kotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class WrittenMeApplication

fun main(args: Array<String>) {
	runApplication<WrittenMeApplication>(*args)
}