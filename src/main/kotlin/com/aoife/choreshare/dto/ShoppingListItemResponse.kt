package com.aoife.choreshare.dto

data class ShoppingListItemResponse(
    val id: Long,
    val name: String,
    val bought: Boolean,
    val addedBy: String,
    val boughtBy: String?
)