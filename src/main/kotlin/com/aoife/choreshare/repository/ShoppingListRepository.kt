package com.aoife.choreshare.repository

import com.aoife.choreshare.model.ShoppingList
import org.springframework.data.jpa.repository.JpaRepository

interface ShoppingListRepository :
    JpaRepository<ShoppingList, Long> {

    fun findByUserId(userId: Long): ShoppingList?

    fun findByGroupId(groupId: Long): ShoppingList?
}