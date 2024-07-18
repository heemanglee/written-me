package com.match.team.migration_kotlin.domain.user

import com.match.team.migration_kotlin.repository.user.UserRepository
import com.match.team.migration_kotlin.util.fail
import lombok.RequiredArgsConstructor
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@RequiredArgsConstructor
@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        val user =
            userRepository.findUserByEmail(username!!) ?: fail("사용자를 조회할 수 없습니다. email = ${username}")

        return CustomUserDetails(
            user.id,
            user.email,
            user.password,
            user.nickName,
            mutableListOf(SimpleGrantedAuthority("ROLE_${user.role.name}"))
        )
    }
}