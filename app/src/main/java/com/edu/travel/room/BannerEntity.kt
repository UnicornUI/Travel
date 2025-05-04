package com.edu.travel.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Banner")
data class BannerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val url: String,
)
