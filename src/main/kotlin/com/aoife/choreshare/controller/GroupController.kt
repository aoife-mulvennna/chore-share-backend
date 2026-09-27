package com.aoife.choreshare.controller

import com.aoife.choreshare.dto.*
import com.aoife.choreshare.service.GroupService
import jakarta.servlet.http.HttpSession
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/groups")
class GroupController(
    private val groupService: GroupService
) {

    @GetMapping
    fun getMyGroups(
        session: HttpSession
    ): List<GroupResponse> {
        return groupService.getMyGroups(
            getUserId(session)
        )
    }

    @PostMapping
    fun createGroup(
        @RequestBody request: CreateGroupRequest,
        session: HttpSession
    ): GroupResponse {
        return groupService.createGroup(
            getUserId(session),
            request.name
        )
    }

    @GetMapping("/{groupId}/members")
    fun getMembers(
        @PathVariable groupId: Long,
        session: HttpSession
    ): List<GroupMemberResponse> {
        return groupService.getMembers(
            getUserId(session),
            groupId
        )
    }

    @PostMapping("/{groupId}/members")
    fun addMember(
        @PathVariable groupId: Long,
        @RequestBody request: AddGroupMemberRequest,
        session: HttpSession
    ) {
        groupService.addMember(
            getUserId(session),
            groupId,
            request.identifier
        )
    }

    @DeleteMapping("/{groupId}/members/{userId}")
    fun removeMember(
        @PathVariable groupId: Long,
        @PathVariable userId: Long,
        session: HttpSession
    ) {
        groupService.removeMember(
            getUserId(session),
            groupId,
            userId
        )
    }

    @GetMapping("/{groupId}/shopping-list")
    fun getShoppingList(
        @PathVariable groupId: Long,
        session: HttpSession
    ): List<ShoppingListItemResponse> {
        return groupService.getShoppingList(
            getUserId(session),
            groupId
        )
    }

    @PostMapping("/{groupId}/shopping-list")
    fun addShoppingListItem(
        @PathVariable groupId: Long,
        @RequestBody request: AddShoppingListItemRequest,
        session: HttpSession
    ): ShoppingListItemResponse {
        return groupService.addShoppingListItem(
            getUserId(session),
            groupId,
            request.name
        )
    }

    @PatchMapping("/{groupId}/shopping-list/{itemId}")
    fun updateBoughtStatus(
        @PathVariable groupId: Long,
        @PathVariable itemId: Long,
        @RequestBody request: UpdateBoughtStatusRequest,
        session: HttpSession
    ): ShoppingListItemResponse {
        return groupService.updateBoughtStatus(
            getUserId(session),
            groupId,
            itemId,
            request.bought
        )
    }

    @DeleteMapping("/{groupId}/shopping-list/{itemId}")
    fun deleteShoppingListItem(
        @PathVariable groupId: Long,
        @PathVariable itemId: Long,
        session: HttpSession
    ) {
        groupService.deleteShoppingListItem(
            getUserId(session),
            groupId,
            itemId
        )
    }

    private fun getUserId(
        session: HttpSession
    ): Long {
        return session.getAttribute("userId") as? Long
            ?: throw ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "You must be logged in"
            )
    }
}