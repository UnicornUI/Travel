package com.edu.travel.room.dao

import androidx.room.*
import com.edu.travel.room.BannerEntity

@Dao
interface BannerDao {

    @Query("SELECT * from Banner")
    suspend fun getAllBanner(): List<BannerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBanner(banner: BannerEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBanners(banners: List<BannerEntity>)

    @Delete
    suspend fun deleteBanner(banner: BannerEntity)

    @Update
    suspend fun updateBanner(banner: BannerEntity)

}