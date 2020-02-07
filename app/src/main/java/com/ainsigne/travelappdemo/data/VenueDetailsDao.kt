package com.ainsigne.travelappdemo.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * The Data Access Object for the [VenueDetails] class.
 */
@Dao
interface VenueDetailsDao {
    @Query("SELECT * FROM venue_details ORDER BY name")
    fun getVenueDetails(): LiveData<List<VenueDetails>>

    @Query("SELECT * FROM venue_details WHERE headerLocation = :headerLocation ORDER BY name")
    fun getVenueDetailsWithLocation(headerLocation: String): LiveData<List<VenueDetails>>

    @Query("SELECT * FROM venue_details WHERE id = :detailId")
    fun getVenueDetail(detailId: String): LiveData<VenueDetails>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(details: VenueDetails)
}
