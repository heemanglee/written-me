package com.match.team.migration_kotlin.domain.file

import com.match.team.migration_kotlin.dto.file.FileDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*

@Component
class FileStore(
    @Value("\${file.dir}")
    val fileDir: String
) {

    fun getFullPath(fileName: String): String {
        return "${fileDir}/${fileName}"
    }

    fun storeFile(multipartFile: MultipartFile): FileDto {
        val originalFileName = multipartFile.originalFilename!!
        val storeFileName = createStoreFileName(originalFileName)

        multipartFile.transferTo(File(getFullPath(storeFileName)))
        return FileDto(originalFileName, storeFileName, getFullPath(storeFileName))
    }

    private fun createStoreFileName(originalFileName: String): String {
        val extension = extractExtension(originalFileName) // 파일 확장자
        return "${UUID.randomUUID()}.${extension}"
    }

    private fun extractExtension(originalFileName: String): String {
        return originalFileName.substring(originalFileName.lastIndexOf(".") + 1)
    }
}