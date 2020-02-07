package com.ainsigne.travelappdemo.data

import com.ainsigne.travelappdemo.interfaces.DetailRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Repository module for handling data operations for the [VenueDetails].
 */
class VenueDetailsRepository (var venueDetailsDao : VenueDetailsDao) :
    DetailRepository {

    override fun getVenueDetails() =
        venueDetailsDao.getVenueDetails()

    override fun getVenueDetail(detailId : String) = venueDetailsDao.getVenueDetail(detailId = detailId)

    override fun getVenueDetailsWithLocation(headerLocation : String) =
            venueDetailsDao.getVenueDetailsWithLocation(headerLocation)


    override fun insert(venueDetails: VenueDetails) {
        CoroutineScope(Dispatchers.IO).launch {
            venueDetailsDao.insert(venueDetails)
        }
    }


}
