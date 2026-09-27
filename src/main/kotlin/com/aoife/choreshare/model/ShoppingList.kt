package com.aoife.choreshare.model

import jakarta.persistence.*

@Entity
@Table(name = "shopping_lists")
data class ShoppingList(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    val user: User? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", unique = true)
    val group: Group? = null
)