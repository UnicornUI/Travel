package com.edu.travel.room.dao

import androidx.room.*
import com.edu.travel.room.PopularEntity

@Dao
interface PopularDao {

    @Query("SELECT * FROM Popular")
    suspend fun getAllPopulars(): List<PopularEntity>

    @Query("SELECT * FROM Popular WHERE id = :id")
    suspend fun getPopularById(id: Int): PopularEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopular(popular: PopularEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopular(populars: List<PopularEntity>)

    @Delete
    suspend fun deletePopular(popular: PopularEntity)

    @Update
    suspend fun updatePopular(popular: PopularEntity)

}