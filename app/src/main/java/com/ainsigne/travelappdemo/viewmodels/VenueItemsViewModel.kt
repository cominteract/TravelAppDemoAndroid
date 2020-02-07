package com.ainsigne.travelappdemo.viewmodels
import androidx.lifecycle.*
import com.ainsigne.travelappdemo.interfaces.ItemRepository

/**
 * The ViewModel for [VenueItemsFragment].
 */
class VenueItemsViewModel internal constructor(repo: ItemRepository) : ViewModel() {


    var venueitems = repo.getVenueItems()

    var travelLocations = repo.getTravelLocations()

}
