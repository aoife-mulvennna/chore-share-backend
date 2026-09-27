package com.aoife.choreshare.repository

import com.aoife.choreshare.model.GroupMember
import org.springframework.data.jpa.repository.JpaRepository

interface GroupMemberRepository : JpaRepository<GroupMember, Long> {

    fun existsByGroupIdAndUserId(
        groupId: Long,
        userId: Long
    ): Boolean

    fun findAllByUserId(
        userId: Long
    ): List<GroupMember>

    fun findAllByGroupId(
        groupId: Long
    ): List<GroupMember>

    fun findByGroupIdAndUserId(
        groupId: Long,
        userId: Long
    ): GroupMember?
}