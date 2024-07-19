package com.match.team.migration_kotlin.controller.file

import com.match.team.migration_kotlin.domain.user.User
import com.match.team.migration_kotlin.service.file.FileService
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/uploads")
@RequiredArgsConstructor
class UploadFileController(
    val fileService: FileService
) {

    @PostMapping
    fun uploadFile(
        @AuthenticationPrincipal user: User,
        @RequestParam multipartFile: MultipartFile
    ): ResponseEntity<String> {
        fileService.uploadUserImage(user, multipartFile)
        return ResponseEntity.ok("ok")
    }
}