package com.aoife.choreshare.model

import jakarta.persistence.*

@Entity
data class ShoppingListItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val name: String = "",

    var bought: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopping_list_id", nullable = false)
    val shoppingList: ShoppingList,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "added_by_user_id", nullable = false)
    val addedBy: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bought_by_user_id")
    var boughtBy: User? = null
)