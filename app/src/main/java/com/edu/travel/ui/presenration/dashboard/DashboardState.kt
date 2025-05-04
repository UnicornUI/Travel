package com.edu.travel.ui.presenration.dashboard

import com.edu.travel.room.BannerEntity
import com.edu.travel.room.CategoryEntity
import com.edu.travel.room.ItemEntity
import com.edu.travel.room.LocationEntity
import com.edu.travel.room.PopularEntity

data class DashboardState(
    val locations: List<LocationEntity> = emptyList(),
    val location: LocationEntity = LocationEntity(-1, ""),
    val banners: List<BannerEntity> = emptyList(),
    val categories: List<CategoryEntity> = emptyList(),
    val category: CategoryEntity = CategoryEntity(-1, "", ""),
    val populars: List<PopularEntity> = emptyList(),
    val recommends: List<ItemEntity> = emptyList(),
)