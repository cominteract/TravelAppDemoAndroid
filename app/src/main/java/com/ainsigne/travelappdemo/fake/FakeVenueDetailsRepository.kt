package com.ainsigne.travelappdemo.fake

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ainsigne.travelappdemo.data.VenueDetails
import com.ainsigne.travelappdemo.interfaces.DetailRepository
import com.ainsigne.travelappdemo.utils.*
import kotlin.collections.ArrayList


/**
 * Fake repository module for handling data operations for [VenueDetails].
 */
class FakeVenueDetailsRepository :
    DetailRepository {

    private val allDetails = ArrayList<VenueDetails>()
    init {
        generateVenueDetails()
    }

    fun generateVenueDetails(){
        if(allDetails.isEmpty()){
            for(i in VenueDescList.indices){
                val venueDetails = VenueDetails(id = VenueIDSList[i], name = VenueNamesList[i], description = VenueDescList[i])
                venueDetails.bestPhoto = HashMap<String,Any>()
                venueDetails.location = HashMap<String,Any>()
                venueDetails.location?.put("lat",randomLatLng[i].latitude)
                venueDetails.location?.put("lng",randomLatLng[i].longitude)
                venueDetails.location?.put("lon",randomLatLng[i].longitude)
                venueDetails.bestPhoto?.put("suffix",VenuePicsList[i])
                venueDetails.bestPhoto?.put("width","")
                venueDetails.bestPhoto?.put("height","")
                venueDetails.bestPhoto?.put("prefix","")

                allDetails.add(venueDetails)
            }
        }
    }

    override fun insert(venueDetails: VenueDetails) {
        allDetails.add(venueDetails)
    }

    override fun getVenueDetails() : LiveData<List<VenueDetails>> {
        val liveData = MutableLiveData<List<VenueDetails>>()

        liveData.value = allDetails
        return liveData
    }

    override fun getVenueDetail(detailId : String) : LiveData<VenueDetails> {
        val liveData = MutableLiveData<VenueDetails>()

        liveData.value = allDetails.first { it.id == detailId }
        return liveData
    }

    override fun getVenueDetailsWithLocation(headerLocation : String)  : LiveData<List<VenueDetails>>  {
        val liveData = MutableLiveData<List<VenueDetails>>()
        liveData.value = allDetails.filter { it.headerLocation == headerLocation }
        return liveData
    }

}
