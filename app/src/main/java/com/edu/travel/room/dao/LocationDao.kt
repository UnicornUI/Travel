package com.edu.travel.room.dao

import androidx.room.*
import com.edu.travel.room.LocationEntity

@Dao
interface LocationDao {

    @Query("SELECT * FROM Location")
    suspend fun getAllLocation(): List<LocationEntity>

    @Query("SELECT * FROM Location Where id = :id")
    suspend fun getLocationById(id: Int): LocationEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: LocationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocations(locations: List<LocationEntity>)

    @Delete
    suspend fun deleteLocation(location: LocationEntity)

    @Update
    suspend fun updateLocation(location: LocationEntity)

}