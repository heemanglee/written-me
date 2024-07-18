package com.match.team.migration_kotlin.domain.user

import com.match.team.migration_kotlin.common.BaseEntity
import com.match.team.migration_kotlin.dto.user.CreateUserRequestDto
import jakarta.persistence.*
import lombok.AccessLevel
import lombok.NoArgsConstructor
import org.springframework.security.crypto.password.PasswordEncoder

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class User(
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(unique = true)
    val email: String,

    @Enumerated(EnumType.STRING)
    val role: Role,

    var nickName: String,
    var password: String,
    val age: Int
) : BaseEntity() {

    companion object {
        fun of(
            request: CreateUserRequestDto,
            passwordEncoder: PasswordEncoder
        ): User {
            return User(
                email = request.email,
                nickName = request.nickName,
                password = passwordEncoder.encode(request.password),
                role = Role.USER,
                age = request.age
            )
        }
    }

    fun updateNickName(nickName: String) {
        this.nickName = nickName
    }

    fun isSamePassword(
        passwordEncoder: PasswordEncoder, rawPassword: String
    ): Boolean {
        return passwordEncoder.matches(rawPassword, this.password)
    }

    fun updatePassword(
        passwordEncoder: PasswordEncoder,
        password: String
    ) {
        this.password = passwordEncoder.encode(password)
    }
}