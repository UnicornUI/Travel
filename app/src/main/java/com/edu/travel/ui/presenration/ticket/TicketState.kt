package com.edu.travel.ui.presenration.ticket

import com.edu.travel.room.ItemEntity
import com.edu.travel.room.PopularEntity

data class TicketState(
    val itemEntity: ItemEntity? = null,
    val popularEntity: PopularEntity? = null,
)
