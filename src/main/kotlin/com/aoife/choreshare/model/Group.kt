package com.aoife.choreshare.model

import jakarta.persistence.*

@Entity
@Table(name = "user_groups")
data class Group(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val name: String = ""
)