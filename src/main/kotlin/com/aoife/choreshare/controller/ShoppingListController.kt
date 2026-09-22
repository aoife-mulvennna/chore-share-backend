package com.aoife.choreshare.controller

import com.aoife.choreshare.dto.AddShoppingListItemRequest
import com.aoife.choreshare.dto.ShoppingListItemResponse
import com.aoife.choreshare.dto.UpdateBoughtStatusRequest
import com.aoife.choreshare.service.ShoppingListService
import jakarta.servlet.http.HttpSession
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/shopping-list")
class ShoppingListController(
    private val shoppingListService: ShoppingListService
) {

    @GetMapping
    fun getShoppingList(
        session: HttpSession
    ): List<ShoppingListItemResponse> {
        val userId = getUserId(session)

        return shoppingListService.getShoppingList(userId)
    }

    @PostMapping
    fun addItem(
        @RequestBody request: AddShoppingListItemRequest,
        session: HttpSession
    ): ShoppingListItemResponse {
        val userId = getUserId(session)

        return shoppingListService.addItem(
            userId,
            request.name
        )
    }

    @PatchMapping("/{id}")
    fun updateBoughtStatus(
        @PathVariable id: Long,
        @RequestBody request: UpdateBoughtStatusRequest,
        session: HttpSession
    ): ShoppingListItemResponse {
        val userId = getUserId(session)

        return shoppingListService.updateBoughtStatus(
            userId,
            id,
            request.bought
        )
    }

    @DeleteMapping("/{id}")
    fun deleteItem(
        @PathVariable id: Long,
        session: HttpSession
    ) {
        val userId = getUserId(session)

        shoppingListService.deleteItem(userId, id)
    }

    private fun getUserId(session: HttpSession): Long {
        return session.getAttribute("userId") as? Long
            ?: throw ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "You must be logged in"
            )
    }
}