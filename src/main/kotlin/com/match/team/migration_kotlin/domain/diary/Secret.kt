package com.match.team.migration_kotlin.domain.diary

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import lombok.AccessLevel
import lombok.NoArgsConstructor

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "secrets")
class Secret(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "secret_id")
    val id: Long? = null,

    var secretNumber: String
) {

    companion object {
        fun of(encryptNumber: String): Secret {
            return Secret(secretNumber = encryptNumber)
        }
    }

    fun updateSecretNumber(encryptNumber: String) {
        this.secretNumber = encryptNumber
    }
}