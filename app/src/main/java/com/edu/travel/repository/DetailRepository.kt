package com.edu.travel.repository

import com.edu.travel.room.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DetailRepository(
    private val appDatabase: AppDatabase
) {

    suspend fun getItemById(id: Int) = withContext(Dispatchers.IO) {
        appDatabase.itemDao().getItemById(id)
    }

    suspend fun getPopularById(id: Int) = withContext(Dispatchers.IO) {
        appDatabase.popularDao().getPopularById(id)
    }

}