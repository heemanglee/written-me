package com.match.team.migration_kotlin.service.user

import com.match.team.migration_kotlin.domain.user.User
import com.match.team.migration_kotlin.dto.user.*
import com.match.team.migration_kotlin.repository.user.UserRepository
import com.match.team.migration_kotlin.util.fail
import com.match.team.migration_kotlin.util.findUserById
import lombok.RequiredArgsConstructor
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @Transactional
    fun createUser(request: CreateUserRequestDto): CreateUserResponseDto {
        val savedUser = userRepository.save(User.of(request, passwordEncoder))
        return CreateUserResponseDto.from(
            userId = savedUser.id!!,
            email = savedUser.email,
            nickName = savedUser.nickName,
            password = savedUser.password,
            age = savedUser.age
        )
    }

    @Transactional(readOnly = true)
    fun getUser(userId: Long): GetUserResponseDto {
        val findUser = userRepository.findUserById(userId)
        return GetUserResponseDto.from(
            userId = findUser.id!!,
            email = findUser.email,
            nickName = findUser.nickName,
            password = findUser.password
        )
    }

    @Transactional(readOnly = true)
    fun checkDuplicateEmail(email: String): Boolean {
        return userRepository.existsByEmail(email)
    }

    @Transactional(readOnly = true)
    fun checkSamePassword(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }

    @Transactional(readOnly = true)
    fun loginUser(request: LoginUserRequestDto) {
        userRepository.findByEmailAndPassword(request.email, request.password)
            ?: fail("사용자를 조회할 수 없습니다. email = ${request.email}, password = ${request.password}")
    }

    @Transactional
    fun checkMySelf(
        user: User,
        request: UpdateNickNameRequestDto
    ): Boolean {
        if (!user.isSamePassword(passwordEncoder, request.password)) {
            return false
        }

        user.updateNickName(request.nickName)
        return true
    }

    @Transactional
    fun updatePassword(
        user: User,
        request: UpdatePasswordRequestDto
    ) {
        user.updatePassword(passwordEncoder, request.password)
    }

    @Transactional(readOnly = true)
    fun samePassword(
        user: User,
        request: CheckEqualsPasswordRequestDto
    ): Boolean {
        return passwordEncoder.matches(request.password, user.password)
    }
}