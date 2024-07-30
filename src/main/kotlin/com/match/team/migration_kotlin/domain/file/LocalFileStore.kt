package com.match.team.migration_kotlin.domain.file

import com.match.team.migration_kotlin.dto.file.FileDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*

//@Profile("local")
//@Component
class LocalFileStore(
    @Value("\${file.dir}")
    val fileDir: String
) : FileStore {

    fun getFullPath(fileName: String): String {
        return "${fileDir}/${fileName}"
    }

    override fun downloadFile(uploadFileName: String): ByteArray {
        TODO("Not yet implemented")
    }

    override fun storeFile(multipartFile: MultipartFile): FileDto {
        val originalFileName = multipartFile.originalFilename!!
        val storeFileName = createStoreFileName(originalFileName)

        multipartFile.transferTo(File(getFullPath(storeFileName)))
        return FileDto(originalFileName, storeFileName, getFullPath(storeFileName))
    }

    override fun getUploadFileUrl(uploadFileName: String): String {
        TODO("Not yet implemented")
    }


    override fun deleteFile(uploadFileName: String) {
        TODO("Not yet implemented")
    }

    private fun createStoreFileName(originalFileName: String): String {
        val extension = extractExtension(originalFileName) // 파일 확장자
        return "${UUID.randomUUID()}.${extension}"
    }

    private fun extractExtension(originalFileName: String): String {
        return originalFileName.substring(originalFileName.lastIndexOf(".") + 1)
    }
}