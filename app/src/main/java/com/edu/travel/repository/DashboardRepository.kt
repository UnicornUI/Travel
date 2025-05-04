package com.edu.travel.repository

import com.edu.travel.room.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DashboardRepository(
    private val database: AppDatabase
) {

    suspend fun getLocation() = withContext(Dispatchers.IO) {
        database.locationDao().getAllLocation()
    }

    suspend fun getBanners() = withContext(Dispatchers.IO) {
        database.bannerDao().getAllBanner()
    }

    suspend fun getCategories() = withContext(Dispatchers.IO) {
        database.categoryDao().getAllCategories()
    }

    suspend fun getPopulars() = withContext(Dispatchers.IO) {
        database.popularDao().getAllPopulars()
    }

    suspend fun getRecommends() = withContext(Dispatchers.IO) {
        database.itemDao().getAllItems()
    }
}