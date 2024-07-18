package com.match.team.migration_kotlin.config

import com.match.team.migration_kotlin.domain.user.CustomUserDetails
import com.match.team.migration_kotlin.domain.user.User
import com.match.team.migration_kotlin.repository.user.UserRepository
import org.springframework.core.MethodParameter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class CustomArgumentResolver(
    private val userRepository: UserRepository
) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.parameterType == User::class.java
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication != null && authentication.principal is CustomUserDetails) {
            val userDetails = authentication.principal as CustomUserDetails
            return userRepository.findUserByEmail(userDetails.email) // SecurityContext로부터 인증된 사용자를 조회하여 @AuthenticationPrincipal을 사용하는 매개변수에 반환한다.
        }
        return null
    }
}