package com.aoife.choreshare.service

import com.aoife.choreshare.dto.ShoppingListItemResponse
import com.aoife.choreshare.model.ShoppingListItem
import com.aoife.choreshare.repository.ShoppingListItemRepository
import com.aoife.choreshare.repository.ShoppingListRepository
import com.aoife.choreshare.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class ShoppingListService(
    private val shoppingListRepository: ShoppingListRepository,
    private val shoppingListItemRepository: ShoppingListItemRepository,
    private val userRepository: UserRepository
) {

    fun getShoppingList(userId: Long): List<ShoppingListItemResponse> {
        val shoppingList = shoppingListRepository.findByUserId(userId)
            ?: throw IllegalArgumentException("Shopping list not found")

        return shoppingListItemRepository
            .findAllByShoppingListId(shoppingList.id)
            .map { it.toResponse() }
    }

    fun addItem(
        userId: Long,
        name: String
    ): ShoppingListItemResponse {
        val user = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("User not found") }

        val shoppingList = shoppingListRepository.findByUserId(userId)
            ?: throw IllegalArgumentException("Shopping list not found")

        val item = ShoppingListItem(
            name = name,
            shoppingList = shoppingList,
            addedBy = user
        )

        return shoppingListItemRepository
            .save(item)
            .toResponse()
    }

    fun updateBoughtStatus(
        userId: Long,
        id: Long,
        bought: Boolean
    ): ShoppingListItemResponse {
        val user = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("User not found") }

        val shoppingList = shoppingListRepository.findByUserId(userId)
            ?: throw IllegalArgumentException("Shopping list not found")

        val item = shoppingListItemRepository
            .findByIdAndShoppingListId(
                id,
                shoppingList.id
            )
            ?: throw IllegalArgumentException(
                "Shopping list item not found"
            )

        item.bought = bought
        item.boughtBy = if (bought) user else null

        return shoppingListItemRepository
            .save(item)
            .toResponse()
    }

    fun deleteItem(
        userId: Long,
        id: Long
    ) {
        val shoppingList = shoppingListRepository.findByUserId(userId)
            ?: throw IllegalArgumentException("Shopping list not found")

        val item = shoppingListItemRepository
            .findByIdAndShoppingListId(
                id,
                shoppingList.id
            )
            ?: throw IllegalArgumentException(
                "Shopping list item not found"
            )

        shoppingListItemRepository.delete(item)
    }

    private fun ShoppingListItem.toResponse() =
        ShoppingListItemResponse(
            id = id,
            name = name,
            bought = bought,
            addedBy = addedBy.nickname ?: addedBy.name,
            boughtBy = boughtBy?.let {
                it.nickname ?: it.name
            }
        )
}