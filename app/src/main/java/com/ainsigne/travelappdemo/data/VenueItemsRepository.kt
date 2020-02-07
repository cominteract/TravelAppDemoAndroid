package com.ainsigne.travelappdemo.data

import com.ainsigne.travelappdemo.interfaces.ItemRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Repository module for handling data operations for the [VenueItem].
 */
class VenueItemsRepository( var venueItemDao : VenueItemDao) : ItemRepository {


//    val venueItems : LiveData<Result<LiveData<List<VenueItem>>>> = liveData {
//        try {
//            emit(Result.success(venueItemDao.getVenueItems()))
//        } catch(ioException: Exception) {
//            emit(Result.failure(ioException))
//        }
//    }

//    fun getVenueItems() =
//        liveData {
//            try {
//                emit(venueItemDao.getVenueItems())
//            } catch(ioException: Exception) {
//                emit(ioException)
//            }
//        }


    override fun getVenueItems() =  venueItemDao.getVenueItems()

    override fun getVenueItem(venueName : String) = venueItemDao.getVenueItem(venueName)

    override fun getTravelLocations() = venueItemDao.getTravelLocations()

    override fun insertVenueItem(venueItem: VenueItem) {

    }

    override fun insertAll(venueItems: List<VenueItem>) {
        CoroutineScope(Dispatchers.IO).launch {
            venueItemDao.insertAll(venueItems)
        }
    }

    override fun insertTravelLocations(travelLocations: TravelLocations) {
        CoroutineScope(Dispatchers.IO).launch {
            venueItemDao.insertLocation(travelLocations)
        }
    }


//    fun getVenueItem(venueItemId : Int) =
//        liveData {
//            try {
//                venueItemDao.getVenueItem(venueItemId)
//            } catch(ioException: Exception) {
//                emit(ioException)
//            }
//        }


}
