package com.match.team.migration_kotlin.service.user

import com.match.team.migration_kotlin.domain.file.FileStore
import com.match.team.migration_kotlin.domain.user.User
import com.match.team.migration_kotlin.dto.user.*
import com.match.team.migration_kotlin.repository.file.UploadFileRepository
import com.match.team.migration_kotlin.repository.user.UserRepository
import com.match.team.migration_kotlin.util.fail
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val uploadFileRepository: UploadFileRepository,

    @Qualifier("s3FileStore")
    private val fileStore: FileStore
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

    @Transactional
    fun deleteProfileImage(
        user: User,
        request: DeleteProfileImageRequestDto
    ) {
        if (isSameImageName(user, request.deleteImageName)) {
            fileStore.deleteFile(user.profileImage!!.uploadFileName)
            uploadFileRepository.deleteById(user.profileImage!!.id!!)
            user.updateImagePath(null)
        } else {
            fail("프로필 이미지를 삭제할 수 없습니다. 이미지 이름 = ${request.deleteImageName}")
        }
    }

    @Transactional(readOnly = true)
    fun getUserImageUploadUrl(user: User): String? {
        if(user.profileImage == null)  {
            return null
        }
        return fileStore.getUploadFileUrl(user.profileImage!!.uploadFileName)
    }

    // 삭제를 하고자하는 이미지와 사용자가 가지고 있는 이미지가 일치하는지 여부 반환
    private fun isSameImageName(user: User, deleteImageName: String): Boolean {
        return user.profileImage?.uploadFileName == deleteImageName
    }
}