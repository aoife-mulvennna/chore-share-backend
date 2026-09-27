package com.aoife.choreshare.repository

import com.aoife.choreshare.model.ShoppingListItem
import org.springframework.data.jpa.repository.JpaRepository

interface ShoppingListItemRepository :
    JpaRepository<ShoppingListItem, Long> {

    fun findAllByShoppingListId(
        shoppingListId: Long
    ): List<ShoppingListItem>

    fun findByIdAndShoppingListId(
        id: Long,
        shoppingListId: Long
    ): ShoppingListItem?
}