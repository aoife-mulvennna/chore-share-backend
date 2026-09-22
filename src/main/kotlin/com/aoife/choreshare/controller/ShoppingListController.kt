package com.aoife.choreshare.controller

import com.aoife.choreshare.dto.UpdateBoughtStatusRequest
import com.aoife.choreshare.model.ShoppingListItem
import com.aoife.choreshare.service.ShoppingListService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/shopping-list")
class ShoppingListController(
    private val shoppingListService: ShoppingListService
) {

    @GetMapping
    fun getShoppingList(): List<ShoppingListItem> {
        return shoppingListService.getShoppingList()
    }

    @PostMapping
    fun addItem(
        @RequestBody request: AddShoppingListItemRequest
    ): ShoppingListItem {
        return shoppingListService.addItem(request.name)
    }

    @PatchMapping("/{id}")
    fun updateBoughtStatus(
        @PathVariable id: Long,
        @RequestBody request: UpdateBoughtStatusRequest
    ): ShoppingListItem {
        return shoppingListService.updateBoughtStatus(id, request.bought)
    }

    @DeleteMapping("/{id}")
    fun deleteItem(
        @PathVariable id: Long
    ) {
        shoppingListService.deleteItem(id)
    }
}

data class AddShoppingListItemRequest(
    val name: String
)