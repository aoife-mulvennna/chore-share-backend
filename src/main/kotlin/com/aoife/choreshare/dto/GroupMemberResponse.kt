package com.aoife.choreshare.dto

data class GroupMemberResponse(
    val id: Long,
    val name: String,
    val nickname: String?,
    val email: String,
    val phoneNumber: String
)