package com.match.team.migration_kotlin.domain.thumbnail

import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.UUID

private val uploadDir = "/Users/hope/Desktop/Desktop/written-me/thumbnails"

@Component
class ThumbnailStore {

    fun uploadThumbnail(multipartFile: MultipartFile): Thumbnail {
        val originalFileName = multipartFile.originalFilename // HTTP 요청시의 파일명
        val uploadFileName = createUploadThumbnailName(originalFileName!!) // 서버에 업로드할 파일명
        multipartFile.transferTo(File(getUploadFullPath(uploadFileName)))
        return Thumbnail.from(originalFileName, uploadFileName)
    }

    private fun getUploadFullPath(uploadFileName: String): String {
        return uploadDir + uploadFileName
    }

    private fun extractExtension(originalFileName: String): String {
        return originalFileName.substring(originalFileName.lastIndexOf(".") + 1)
    }

    private fun createUploadThumbnailName(originalFileName: String): String {
        return UUID.randomUUID().toString() + "." + extractExtension(originalFileName)
    }
}