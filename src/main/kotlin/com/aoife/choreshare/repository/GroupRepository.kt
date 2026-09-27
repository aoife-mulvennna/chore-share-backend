package com.aoife.choreshare.repository

import com.aoife.choreshare.model.Group
import org.springframework.data.jpa.repository.JpaRepository

interface GroupRepository : JpaRepository<Group, Long>