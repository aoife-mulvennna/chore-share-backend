package com.aoife.choreshare.dto

data class UserResponse(
    val id: Long,
    val name: String,
    val nickname: String?,
    val phoneNumber: String,
    val email: String
)