package com.aoife.choreshare.dto

data class RegisterRequest(
    val name: String,
    val nickname: String?,
    val phoneNumber: String,
    val email: String,
    val password: String
)