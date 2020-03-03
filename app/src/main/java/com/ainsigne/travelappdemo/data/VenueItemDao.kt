package com.ainsigne.travelappdemo.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * The Data Access Object for the [VenueDetails] class.
 */
@Dao
interface VenueItemDao {
    @Query("SELECT * FROM venue_items ORDER BY venueItemId")
    fun getVenueItems(): LiveData<List<VenueItem>>

    @Query("SELECT * FROM venue_items WHERE venue_name = :venueName_")
    fun getVenueItem(venueName_: String): LiveData<VenueItem>

    @Query("SELECT * FROM venue_items WHERE venue_id = :venueId_")
    fun getVenueItemFromId(venueId_: String): LiveData<VenueItem>

    @Query("SELECT * FROM venue_items WHERE latlng = :latlng_")
    fun getVenueItemFromNearby(latlng_: String): LiveData<List<VenueItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<VenueItem>)

    @Query("SELECT * FROM travel_locations ORDER BY locationId")
    fun getTravelLocations(): LiveData<List<TravelLocations>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(travelLocations: TravelLocations)

}
