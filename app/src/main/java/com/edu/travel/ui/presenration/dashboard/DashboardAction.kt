package com.edu.travel.ui.presenration.dashboard

import com.edu.travel.room.CategoryEntity
import com.edu.travel.room.LocationEntity

sealed interface DashboardAction {

    data class ChangedLocationAction(val location: LocationEntity): DashboardAction
    data class ChangedCategoryAction(val category: CategoryEntity): DashboardAction

}