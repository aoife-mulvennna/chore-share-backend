package com.aoife.choreshare.model

import jakarta.persistence.*

@Entity
@Table(
    name = "group_members",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["group_id", "user_id"])
    ]
)
data class GroupMember(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    val group: Group,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User
)