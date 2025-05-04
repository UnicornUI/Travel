package com.edu.travel.ui.presenration.detail

import com.edu.travel.room.ItemEntity
import com.edu.travel.room.PopularEntity

data class DetailState(
    val itemEntity: ItemEntity? = null,
    val popularEntity: PopularEntity? = null,
)
