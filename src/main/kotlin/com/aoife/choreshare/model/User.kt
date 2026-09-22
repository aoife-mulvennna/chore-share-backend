package com.aoife.choreshare.model

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val name: String = "",

    val nickname: String? = null,

    @Column(unique = true)
    val phoneNumber: String = "",

    @Column(unique = true)
    val email: String = "",

    val passwordHash: String = ""
)