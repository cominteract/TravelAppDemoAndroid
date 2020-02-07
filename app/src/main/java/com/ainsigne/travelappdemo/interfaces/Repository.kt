package com.ainsigne.travelappdemo.interfaces

import androidx.lifecycle.LiveData
import com.ainsigne.travelappdemo.data.*


/**
 * Common interface for the repositories so they can be interswapped for mocking objects
 */

interface DetailRepository {
    fun getVenueDetails() : LiveData<List<VenueDetails>>

    fun getVenueDetail(detailId : String)
 : LiveData<VenueDetails>
    fun getVenueDetailsWithLocation(headerLocation : String)  : LiveData<List<VenueDetails>>

    fun insert(venueDetails: VenueDetails)
}

interface ItemRepository {
    fun getVenueItems() : LiveData<List<VenueItem>>

    fun getVenueItem(venueName : String) : LiveData<VenueItem>

    fun getTravelLocations() : LiveData<List<TravelLocations>>

    fun insertVenueItem(venueItem: VenueItem)

    fun insertAll(venueItems : List<VenueItem>)

    fun insertTravelLocations(travelLocations: TravelLocations)

}

interface FaveRepository {


    fun createVenueFavorites(faveId: String)

    fun removeVenueFavorites(venueFavorites: VenueFavorites)

    fun getVenueFavorites() : LiveData<List<VenueFavorites>>

    fun getVenueAndFavorites() : LiveData<List<VenueAndFavorites>>



}