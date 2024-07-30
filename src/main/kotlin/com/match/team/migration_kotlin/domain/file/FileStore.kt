package com.match.team.migration_kotlin.domain.file

import com.match.team.migration_kotlin.dto.file.FileDto
import org.springframework.web.multipart.MultipartFile

interface FileStore {

    fun storeFile(multipartFile: MultipartFile): FileDto

    fun downloadFile(uploadFileName: String): ByteArray

    fun deleteFile(uploadFileName: String)

    fun getUploadFileUrl(uploadFileName: String): String
}