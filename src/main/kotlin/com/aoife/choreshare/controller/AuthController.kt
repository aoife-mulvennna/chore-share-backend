package com.aoife.choreshare.controller

import com.aoife.choreshare.dto.LoginRequest
import com.aoife.choreshare.dto.RegisterRequest
import com.aoife.choreshare.dto.UserResponse
import com.aoife.choreshare.service.AuthService
import jakarta.servlet.http.HttpSession
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
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
}