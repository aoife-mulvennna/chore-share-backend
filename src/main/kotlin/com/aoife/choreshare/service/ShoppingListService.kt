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

    fun updateBoughtStatus(id: Long, bought: Boolean): ShoppingListItem {
        val item = shoppingListRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Shopping list item not found") }

        item.bought = bought

        return shoppingListRepository.save(item)
    }

    fun deleteItem(id: Long) {
        if (!shoppingListRepository.existsById(id)) {
            throw IllegalArgumentException("Shopping list item not found")
        }

        shoppingListRepository.deleteById(id)
    }
}