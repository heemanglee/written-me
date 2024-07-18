package com.match.team.migration_kotlin.repository.diray

import com.match.team.migration_kotlin.domain.diary.Diary
import org.springframework.data.jpa.repository.JpaRepository

interface DiaryRepository : JpaRepository<Diary, Long>, DiaryRepositoryCustom {

}