package com.match.team.migration_kotlin.repository.file

import com.match.team.migration_kotlin.domain.file.UploadFile
import org.springframework.data.jpa.repository.JpaRepository

interface UploadFileRepository : JpaRepository<UploadFile, Long> {

}
