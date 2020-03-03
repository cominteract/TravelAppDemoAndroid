package com.ainsigne.travelappdemo.viewmodels
import androidx.lifecycle.*
import com.ainsigne.travelappdemo.data.TravelLocations
import com.ainsigne.travelappdemo.data.VenueItem
import com.ainsigne.travelappdemo.interfaces.ItemRepository

/**
 * The ViewModel for [VenueItemsFragment].
 */
class VenueItemsViewModel internal constructor(repo: ItemRepository) : ViewModel() {

    private val repo_ = repo
    var venueitems = repo.getVenueItems()

    fun venueItemsNearby(travelLocations: TravelLocations) : LiveData<List<VenueItem>>{
            return repo_.getNearbyVenueItems(travelLocations)
    }



    var travelLocations = repo.getTravelLocations()

}
