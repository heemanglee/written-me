package com.match.team.migration_kotlin.service.file

import com.match.team.migration_kotlin.domain.file.FileStore
import com.match.team.migration_kotlin.domain.file.UploadFile
import com.match.team.migration_kotlin.domain.user.User
import com.match.team.migration_kotlin.dto.file.FileDto
import com.match.team.migration_kotlin.repository.file.UploadFileRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
@RequiredArgsConstructor
class FileService(
    private val fileRepository: UploadFileRepository,
    private val fileStore: FileStore
) {

    @Transactional
    fun uploadUserImage(user: User, multipartFile: MultipartFile): FileDto {
        val requestDto = fileStore.storeFile(multipartFile)
        val uploadFile = UploadFile.from(
            requestDto.uploadFileName,
            requestDto.storeFileName, requestDto.uploadFullPath
        )
        val uploadedFile = fileRepository.save(uploadFile)
        user.updateImagePath(uploadedFile)
        return requestDto
    }

}