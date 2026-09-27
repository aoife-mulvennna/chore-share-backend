package com.aoife.choreshare.service

import com.aoife.choreshare.dto.*
import com.aoife.choreshare.model.Group
import com.aoife.choreshare.model.GroupMember
import com.aoife.choreshare.model.ShoppingList
import com.aoife.choreshare.model.ShoppingListItem
import com.aoife.choreshare.repository.*
import org.springframework.stereotype.Service

@Service
class GroupService(
    private val groupRepository: GroupRepository,
    private val groupMemberRepository: GroupMemberRepository,
    private val userRepository: UserRepository,
    private val shoppingListRepository: ShoppingListRepository,
    private val shoppingListItemRepository: ShoppingListItemRepository
) {

    fun createGroup(
        currentUserId: Long,
        name: String
    ): GroupResponse {
        val user = userRepository.findById(currentUserId)
            .orElseThrow { IllegalArgumentException("User not found") }

        val group = groupRepository.save(
            Group(name = name)
        )

        groupMemberRepository.save(
            GroupMember(
                group = group,
                user = user
            )
        )

        shoppingListRepository.save(
            ShoppingList(
                group = group
            )
        )

        return GroupResponse(
            id = group.id,
            name = group.name
        )
    }

    fun getMyGroups(
        currentUserId: Long
    ): List<GroupResponse> {
        return groupMemberRepository
            .findAllByUserId(currentUserId)
            .map {
                GroupResponse(
                    id = it.group.id,
                    name = it.group.name
                )
            }
    }

    fun addMember(
        currentUserId: Long,
        groupId: Long,
        identifier: String
    ) {
        requireMembership(currentUserId, groupId)

        val group = groupRepository.findById(groupId)
            .orElseThrow { IllegalArgumentException("Group not found") }

        val userToAdd =
            if (identifier.contains("@")) {
                userRepository.findByEmail(identifier.lowercase())
            } else {
                userRepository.findByPhoneNumber(identifier)
            }
                ?: throw IllegalArgumentException("User not found")

        if (
            groupMemberRepository.existsByGroupIdAndUserId(
                groupId,
                userToAdd.id
            )
        ) {
            throw IllegalArgumentException(
                "User is already in this group"
            )
        }

        groupMemberRepository.save(
            GroupMember(
                group = group,
                user = userToAdd
            )
        )
    }

    fun removeMember(
        currentUserId: Long,
        groupId: Long,
        userIdToRemove: Long
    ) {
        requireMembership(currentUserId, groupId)

        val membership =
            groupMemberRepository.findByGroupIdAndUserId(
                groupId,
                userIdToRemove
            )
                ?: throw IllegalArgumentException(
                    "User is not in this group"
                )

        groupMemberRepository.delete(membership)
    }

    fun getMembers(
        currentUserId: Long,
        groupId: Long
    ): List<GroupMemberResponse> {
        requireMembership(currentUserId, groupId)

        return groupMemberRepository
            .findAllByGroupId(groupId)
            .map {
                GroupMemberResponse(
                    id = it.user.id,
                    name = it.user.name,
                    nickname = it.user.nickname,
                    email = it.user.email,
                    phoneNumber = it.user.phoneNumber
                )
            }
    }

    fun getShoppingList(
        currentUserId: Long,
        groupId: Long
    ): List<ShoppingListItemResponse> {
        requireMembership(currentUserId, groupId)

        val shoppingList =
            shoppingListRepository.findByGroupId(groupId)
                ?: throw IllegalArgumentException(
                    "Group shopping list not found"
                )

        return shoppingListItemRepository
            .findAllByShoppingListId(shoppingList.id)
            .map { it.toResponse() }
    }

    fun addShoppingListItem(
        currentUserId: Long,
        groupId: Long,
        name: String
    ): ShoppingListItemResponse {
        requireMembership(currentUserId, groupId)

        val user = userRepository.findById(currentUserId)
            .orElseThrow { IllegalArgumentException("User not found") }

        val shoppingList =
            shoppingListRepository.findByGroupId(groupId)
                ?: throw IllegalArgumentException(
                    "Group shopping list not found"
                )

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
        currentUserId: Long,
        groupId: Long,
        itemId: Long,
        bought: Boolean
    ): ShoppingListItemResponse {
        requireMembership(currentUserId, groupId)

        val user = userRepository.findById(currentUserId)
            .orElseThrow { IllegalArgumentException("User not found") }

        val shoppingList =
            shoppingListRepository.findByGroupId(groupId)
                ?: throw IllegalArgumentException(
                    "Group shopping list not found"
                )

        val item =
            shoppingListItemRepository.findByIdAndShoppingListId(
                itemId,
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

    fun deleteShoppingListItem(
        currentUserId: Long,
        groupId: Long,
        itemId: Long
    ) {
        requireMembership(currentUserId, groupId)

        val shoppingList =
            shoppingListRepository.findByGroupId(groupId)
                ?: throw IllegalArgumentException(
                    "Group shopping list not found"
                )

        val item =
            shoppingListItemRepository.findByIdAndShoppingListId(
                itemId,
                shoppingList.id
            )
                ?: throw IllegalArgumentException(
                    "Shopping list item not found"
                )

        shoppingListItemRepository.delete(item)
    }

    private fun requireMembership(
        userId: Long,
        groupId: Long
    ) {
        if (
            !groupMemberRepository.existsByGroupIdAndUserId(
                groupId,
                userId
            )
        ) {
            throw IllegalArgumentException(
                "You are not a member of this group"
            )
        }
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