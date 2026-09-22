package com.aoife.choreshare.controller

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
}

data class AddShoppingListItemRequest(
    val name: String
)