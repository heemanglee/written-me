package com.match.team.migration_kotlin.domain.file

import com.match.team.migration_kotlin.common.BaseEntity
import jakarta.persistence.*
import lombok.AccessLevel
import lombok.NoArgsConstructor

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class UploadFile(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "upload_file_id")
    val id: Long? = null,

    val originalFileName: String,
    val uploadFileName: String,
    val uploadFullPath: String

) : BaseEntity() {

    companion object {
        fun from(
            originalFileName: String,
            uploadFileName: String,
            uploadFullPath: String
        ): UploadFile {
            return UploadFile(
                id = null,
                originalFileName = originalFileName,
                uploadFileName = uploadFileName,
                uploadFullPath = uploadFullPath
            )
        }
    }
}