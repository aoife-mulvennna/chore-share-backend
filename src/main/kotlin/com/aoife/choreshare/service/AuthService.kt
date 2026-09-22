package com.aoife.choreshare.service

import com.aoife.choreshare.dto.LoginRequest
import com.aoife.choreshare.dto.RegisterRequest
import com.aoife.choreshare.model.User
import com.aoife.choreshare.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun register(request: RegisterRequest): User {
        if (userRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("An account with this email already exists")
        }

        val passwordHash = requireNotNull(
            passwordEncoder.encode(request.password)
        )

        val user = User(
            name = request.name,
            nickname = request.nickname,
            phoneNumber = request.phoneNumber,
            email = request.email.lowercase(),
            passwordHash = passwordHash
        )

        return userRepository.save(user)
    }
    fun login(request: LoginRequest): User {
        val user = userRepository.findByEmail(request.email.lowercase())
            ?: throw IllegalArgumentException("Invalid email or password")

        val passwordMatches = passwordEncoder.matches(
            request.password,
            user.passwordHash
        )

        if (!passwordMatches) {
            throw IllegalArgumentException("Invalid email or password")
        }

        return user
    }
}