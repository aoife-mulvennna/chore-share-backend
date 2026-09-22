package com.aoife.choreshare.repository

import com.aoife.choreshare.model.ShoppingListItem
import org.springframework.data.jpa.repository.JpaRepository

interface ShoppingListRepository : JpaRepository<ShoppingListItem, Long>