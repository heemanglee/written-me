package com.match.team.migration_kotlin.domain.thumbnail

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import lombok.AccessLevel
import lombok.NoArgsConstructor

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class Thumbnail (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "thumbnail_id")
    val id: Long? = null,

    val originalFileName: String,
    val uploadFileName: String,
) {

    companion object {
        fun from(
            originalFileName: String,
            uploadFileName: String,
        ): Thumbnail {
            return Thumbnail(
                id = null,
                originalFileName = originalFileName,
                uploadFileName = uploadFileName
            )
        }
    }
}