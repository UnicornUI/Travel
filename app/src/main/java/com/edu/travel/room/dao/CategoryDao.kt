package com.edu.travel.room.dao

import androidx.room.*
import com.edu.travel.room.CategoryEntity

@Dao
interface CategoryDao {

    @Query("SELECT * FROm Category")
    suspend fun getAllCategories(): List<CategoryEntity>

    @Query("SELECT * FROM Category WHERE id = :id")
    suspend fun getCategoryById(id: Int): CategoryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<CategoryEntity>)

    @Delete
    suspend fun deleteCategory(category: CategoryEntity)

    @Update
    suspend fun updateCategory(category: CategoryEntity)
}