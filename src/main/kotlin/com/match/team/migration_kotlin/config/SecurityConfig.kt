package com.match.team.migration_kotlin.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.match.team.migration_kotlin.domain.user.CustomUserDetailsService
import com.match.team.migration_kotlin.domain.user.Role
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.RequiredArgsConstructor
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val accessDeniedHandler: JwtAccessDeniedHandler,
    private val userDetailsService: CustomUserDetailsService
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { csrfConfig ->
                run {
                    csrfConfig.disable()
                }
            }
            .headers { headerConfig ->
                run {
                    headerConfig.frameOptions { frameOptionsConfig ->
                        frameOptionsConfig.disable()
                    }
                }
            }
            .authorizeHttpRequests { authorizeRequests ->
                run {
                    authorizeRequests
                        .requestMatchers("/", "/users/sign-up/**", "/users/sign-in/**", "/api/users/sign-up/**", "/api/users/sign-in/**")
                        .permitAll()
                        .requestMatchers("/css/**", "/js/**")
                        .permitAll()
                        .requestMatchers("/diarys/**", "/users/profile/**").hasRole(Role.USER.name)
                        .requestMatchers("/chats/**").hasRole(Role.USER.name)
                        .requestMatchers("/uploads/**").hasRole(Role.USER.name)
                        .requestMatchers("/api/**").hasRole(Role.USER.name)
                        .anyRequest().authenticated()
                };
            }
            .formLogin { formLoginConfig ->
                run {
                    formLoginConfig
                        .loginPage("/users/sign-in")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .loginProcessingUrl("/users/sign-in")
                        .defaultSuccessUrl("/", true)
                }
            }
            // 로그아웃 설정
            .logout { logoutConfig ->
                run {
                    logoutConfig.logoutUrl("/users/sign-out")
                        .logoutSuccessUrl("/")
                        .addLogoutHandler { request, response, authentication ->
                            run {
                                val session = request.session
                                session.invalidate()
                            }
                        }
                        .logoutSuccessHandler { request, response, authentication ->
                            run {
                                response.sendRedirect("/")
                            }
                        }
                }
            }
            .userDetailsService(userDetailsService)

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}

@Component
@RequiredArgsConstructor
class JwtAuthenticationEntryPoint(
    private val objectMapper: ObjectMapper
) : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        response!!.characterEncoding = "UTF-8"
        response!!.status = HttpStatus.UNAUTHORIZED.value()
        response!!.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.write(
            objectMapper.writeValueAsString(
                ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Spring security unauthorized")
            )
        )
    }
}

@Component
@RequiredArgsConstructor
class JwtAccessDeniedHandler(
    private val objectMapper: ObjectMapper
) : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        accessDeniedException: AccessDeniedException?
    ) {
        response!!.characterEncoding = "UTF-8"
        response!!.status = HttpStatus.FORBIDDEN.value()
        response!!.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.write(
            objectMapper.writeValueAsString(
                ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Spring security forbidden")
            )
        )
    }
}