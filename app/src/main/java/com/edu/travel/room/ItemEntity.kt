package com.edu.travel.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="Item")
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val address: String,
    val bed: Int,
    val dateTour: String,
    val description: String,
    val distance: String,
    val duration: String,
    val pic: String,
    val price: Int,
    val score: Double,
    val timeTour: String,
    val title: String,
    val tourGuideName: String,
    val tourGuidePhone: String,
    val tourGuidePic: String,
)