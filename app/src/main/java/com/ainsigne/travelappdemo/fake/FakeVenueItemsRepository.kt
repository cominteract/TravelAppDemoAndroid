package com.ainsigne.travelappdemo.fake

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ainsigne.travelappdemo.data.*
import com.ainsigne.travelappdemo.interfaces.ItemRepository
import com.ainsigne.travelappdemo.utils.*
import com.google.gson.internal.LinkedTreeMap

/**
 * Fake repository module for handling data operations for [VenueItem].
 */
class FakeVenueItemsRepository : ItemRepository {

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
    private val allVenues = ArrayList<VenueItem>()
    private val venueLiveData = MutableLiveData<List<VenueItem>>()
    init {
        generateVenues()
    }



    fun generateVenues() {
        if(allVenues.isEmpty()){
            for(i in VenueDescList.indices){

                var lat = randomLatLng[0].latitude
                var lon = randomLatLng[0].longitude
                val venue = Venue(id = VenueIDSList[i] , name = VenueNamesList[i], categories = ArrayList<HashMap<String,Any>>())
                venue.categories?.add(HashMap<String, Any>())
                venue.categories?.let {
                    it[0]["icon"] = LinkedTreeMap<String, Any>()
                    (it[0]["icon"] as LinkedTreeMap<String, Any>)["suffix"] = VenueIconsList[i]

                    (it[0]["icon"] as LinkedTreeMap<String, Any>)["width"] = ""
                    (it[0]["icon"] as LinkedTreeMap<String, Any>)["height"] = ""
                    (it[0]["icon"] as LinkedTreeMap<String, Any>)["prefix"] = ""
                }

                val venueItem = VenueItem(venueItemId = VenueIDSList[i].toInt(), venue = venue, latlng = "${lat},${lon}")
                allVenues.add(venueItem)
            }
        }
    }



    override fun insertVenueItem(venueItem: VenueItem) {
        allVenues.add(venueItem)
    }

    override fun insertAll(venueItems: List<VenueItem>) {

    }

    override fun insertTravelLocations(travelLocations: TravelLocations) {
    }

    override fun getNearbyVenueItems(travelLocations: TravelLocations): LiveData<List<VenueItem>> {

        venueLiveData.value = allVenues.filter { it.latlng == "${travelLocations.lat},${travelLocations.lon}"}
        return venueLiveData
    }

    override fun getVenueItems() : LiveData<List<VenueItem>> {
        venueLiveData.value = allVenues
        return venueLiveData
    }

    override fun getVenueItem(venueName : String) : LiveData<VenueItem>{
        val liveData = MutableLiveData<VenueItem>()
        liveData.value = allVenues.first { it.venue?.name == venueName }
        return liveData
    }

    override fun getTravelLocations() : LiveData<List<TravelLocations>> {
        val liveData = MutableLiveData<List<TravelLocations>>()
        val travelLocations = ArrayList<TravelLocations>()
        val lat = randomLatLng[0].latitude
        val lon = randomLatLng[0].longitude
        travelLocations.add(TravelLocations(1,lat,lon))
        liveData.value = travelLocations
        return liveData
    }


//    fun getVenueItem(venueItemId : Int) =
//        liveData {
//            try {
//                venueItemDao.getVenueItem(venueItemId)
//            } catch(ioException: Exception) {
//                emit(ioException)
//            }
//        }


    companion object {

        // For Singleton instantiation
        @Volatile private var instance: FakeVenueItemsRepository? = null

        fun getInstance() =
                instance ?: synchronized(this) {
                    instance ?: FakeVenueItemsRepository().also { instance = it }
                }
    }
}
