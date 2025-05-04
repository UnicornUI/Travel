package com.edu.travel.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="Location")
data class LocationEntity(
    @PrimaryKey
    val id: Int,
    val loc: String,
)