package com.edu.travel.room.dao

import androidx.room.*
import com.edu.travel.room.ItemEntity

@Dao
interface ItemDao {

    @Query("SELECT * FROM Item")
    suspend fun getAllItems(): List<ItemEntity>

    @Query("SELECT * FROM Item where id = :id")
    suspend fun getItemById(id: Int): ItemEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<ItemEntity>)

    @Delete
    suspend fun deleteItem(item: ItemEntity)

    @Update
    suspend fun updateItem(item: ItemEntity)

}
