package com.edu.travel.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.edu.travel.room.dao.BannerDao
import com.edu.travel.room.dao.CategoryDao
import com.edu.travel.room.dao.ItemDao
import com.edu.travel.room.dao.LocationDao
import com.edu.travel.room.dao.PopularDao

@Database(entities = [
    BannerEntity::class,
    CategoryEntity::class,
    ItemEntity::class,
    LocationEntity::class,
    PopularEntity::class,
], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun bannerDao(): BannerDao
    abstract fun categoryDao(): CategoryDao
    abstract fun itemDao(): ItemDao
    abstract fun locationDao(): LocationDao
    abstract fun popularDao(): PopularDao


    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        private const val DATABASE_NAME = "database.db"

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it}
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME,
            ).createFromAsset(DATABASE_NAME)
             .build()
        }



    }







}