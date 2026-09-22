package com.aoife.choreshare.service

import com.aoife.choreshare.model.ShoppingListItem
import com.aoife.choreshare.repository.ShoppingListRepository
import org.springframework.stereotype.Service

@Service
class ShoppingListService(
    private val shoppingListRepository: ShoppingListRepository
) {
    fun getShoppingList(): List<ShoppingListItem> {
        return shoppingListRepository.findAll()
    }

    fun addItem(name: String): ShoppingListItem {
        return shoppingListRepository.save(
            ShoppingListItem(name = name)
        )
    }
}