package com.aoife.choreshare.service

import com.aoife.choreshare.dto.ShoppingListItemResponse
import com.aoife.choreshare.model.ShoppingListItem
import com.aoife.choreshare.repository.ShoppingListRepository
import com.aoife.choreshare.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class ShoppingListService(
    private val shoppingListRepository: ShoppingListRepository,
    private val userRepository: UserRepository
) {

    fun getShoppingList(userId: Long): List<ShoppingListItemResponse> {
        return shoppingListRepository
            .findAllByUserId(userId)
            .map { it.toResponse() }
    }

    fun addItem(
        userId: Long,
        name: String
    ): ShoppingListItemResponse {
        val user = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("User not found") }

        val item = ShoppingListItem(
            name = name,
            user = user
        )

        return shoppingListRepository.save(item).toResponse()
    }

    fun updateBoughtStatus(
        userId: Long,
        id: Long,
        bought: Boolean
    ): ShoppingListItemResponse {
        val item = shoppingListRepository
            .findByIdAndUserId(id, userId)
            ?: throw IllegalArgumentException("Shopping list item not found")

        item.bought = bought

        return shoppingListRepository.save(item).toResponse()
    }

    fun deleteItem(
        userId: Long,
        id: Long
    ) {
        val item = shoppingListRepository
            .findByIdAndUserId(id, userId)
            ?: throw IllegalArgumentException("Shopping list item not found")

        shoppingListRepository.delete(item)
    }

    private fun ShoppingListItem.toResponse() =
        ShoppingListItemResponse(
            id = id,
            name = name,
            bought = bought
        )
}