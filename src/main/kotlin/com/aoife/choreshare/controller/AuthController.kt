package com.aoife.choreshare.controller

import com.aoife.choreshare.dto.LoginRequest
import com.aoife.choreshare.dto.RegisterRequest
import com.aoife.choreshare.dto.UserResponse
import com.aoife.choreshare.repository.UserRepository
import com.aoife.choreshare.service.AuthService
import jakarta.servlet.http.HttpSession
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService,
    private val userRepository: UserRepository
) {

    @PostMapping("/register")
    fun register(
        @RequestBody request: RegisterRequest
    ): UserResponse {
        val user = authService.register(request)

        return UserResponse(
            id = user.id,
            name = user.name,
            nickname = user.nickname,
            phoneNumber = user.phoneNumber,
            email = user.email
        )
    }

    @PostMapping("/login")
    fun login(
        @RequestBody request: LoginRequest,
        session: HttpSession
    ): UserResponse {
        val user = authService.login(request)

        session.setAttribute("userId", user.id)

        return UserResponse(
            id = user.id,
            name = user.name,
            nickname = user.nickname,
            phoneNumber = user.phoneNumber,
            email = user.email
        )
    }

    @GetMapping("/me")
    fun me(
        session: HttpSession
    ): UserResponse {
        val userId = session.getAttribute("userId") as? Long
            ?: throw ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Not logged in"
            )

        val user = userRepository.findById(userId)
            .orElseThrow {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User not found"
                )
            }

        return UserResponse(
            id = user.id,
            name = user.name,
            nickname = user.nickname,
            phoneNumber = user.phoneNumber,
            email = user.email
        )
    }

    @PostMapping("/logout")
    fun logout(
        session: HttpSession
    ) {
        session.invalidate()
    }
}