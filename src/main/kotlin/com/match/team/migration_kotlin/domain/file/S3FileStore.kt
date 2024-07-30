package com.match.team.migration_kotlin.domain.file

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.GetObjectRequest
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.util.IOUtils
import com.match.team.migration_kotlin.dto.file.FileDto
import com.mysema.commons.lang.URLEncoder
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Component
@RequiredArgsConstructor
@Primary
@Profile("local")
class S3FileStore(
    @Value("\${cloud.aws.s3.bucket}")
    private val bucket: String,

    private val amazonS3: AmazonS3
) : FileStore {

    fun getFullPath(fileName: String): String {
        return "${bucket}/${fileName}"
    }

    override fun storeFile(multipartFile: MultipartFile): FileDto {
        val originalFileName = multipartFile.originalFilename!!
        val storeFileName = createStoreFileName(originalFileName)

        val metaData = ObjectMetadata()
        metaData.contentType = multipartFile.contentType
        metaData.contentLength = multipartFile.size

        amazonS3.putObject(bucket, storeFileName, multipartFile.inputStream, metaData)

        return FileDto(originalFileName, storeFileName, getFullPath(storeFileName))
    }

    override fun downloadFile(uploadFileName: String): ByteArray {
        val o = amazonS3.getObject(GetObjectRequest(bucket, uploadFileName))
        val objectInputStream = o.objectContent
        val bytes = IOUtils.toByteArray(objectInputStream)

        val fileName = URLEncoder.encodeParam(uploadFileName, "UTF-8")
        val httpHeaders = HttpHeaders()
        httpHeaders.contentType = MediaType.APPLICATION_OCTET_STREAM
        httpHeaders.contentLength = bytes.size.toLong()
        httpHeaders.setContentDispositionFormData("attachment", fileName)

        return bytes
    }

    override fun getUploadFileUrl(uploadFileName: String): String {
        return amazonS3.getUrl(bucket, uploadFileName).toString()
    }

    override fun deleteFile(uploadFileName: String) {
        amazonS3.deleteObject(bucket, uploadFileName)
    }

    private fun createStoreFileName(originalFileName: String): String {
        val extension = extractExtension(originalFileName) // 파일 확장자
        return "${UUID.randomUUID()}.${extension}"
    }

    private fun extractExtension(originalFileName: String): String {
        return originalFileName.substring(originalFileName.lastIndexOf(".") + 1)
    }
}