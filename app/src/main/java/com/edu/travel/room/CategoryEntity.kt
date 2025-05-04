package com.edu.travel.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="Category")
data class CategoryEntity(
    @PrimaryKey
    val id: Int = 0,
    val imagePath : String,
    val name: String,
)