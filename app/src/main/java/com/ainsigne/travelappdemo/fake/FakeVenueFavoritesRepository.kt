package com.ainsigne.travelappdemo.fake

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ainsigne.travelappdemo.data.VenueAndFavorites
import com.ainsigne.travelappdemo.data.VenueDetails
import com.ainsigne.travelappdemo.data.VenueFavorites
import com.ainsigne.travelappdemo.interfaces.FaveRepository
import com.ainsigne.travelappdemo.utils.VenueDescList
import com.ainsigne.travelappdemo.utils.VenueIDSList
import com.ainsigne.travelappdemo.utils.VenueNamesList
import com.ainsigne.travelappdemo.utils.VenuePicsList
/**
 * Fake repository module for handling data operations for [VenueFavorites].
 */
class FakeVenueFavoritesRepository (
    ) : FaveRepository {

    private val venueFaves = ArrayList<VenueFavorites>()
    private val venueAndFaves = ArrayList<VenueAndFavorites>()
    private val allDetails = ArrayList<VenueDetails>()
    init {
        generateVenueFaves()
    }

    private fun generateVenueFaves()
    {

        if(allDetails.isEmpty()){
            for(i in VenueDescList.indices){
                val venueDetails = VenueDetails(id = VenueIDSList[i], name = VenueNamesList[i], description = VenueDescList[i])
                venueDetails.fakeUrl = VenuePicsList[i]
                allDetails.add(venueDetails)
            }
        }
        if(venueFaves.isNullOrEmpty()){
            for (i in 0..3){
                val venueFavorites = VenueFavorites(favedetailId = VenueIDSList[i])
                val venueAndFavorites = VenueAndFavorites()



                venueAndFavorites.venueFavorites = mutableListOf(venueFavorites)
                venueAndFavorites.details = allDetails[i]
                venueFaves.add(venueFavorites)
                venueAndFaves.add(venueAndFavorites)
            }
        }
    }
    override fun createVenueFavorites(faveId: String) {
        if(venueFaves.map { it.favedetailId == faveId }.isNullOrEmpty()){
            val venueFavorites = VenueFavorites(favedetailId = faveId)
            val venueAndFavorites = VenueAndFavorites()
            venueAndFavorites.venueFavorites = mutableListOf(venueFavorites)
            venueAndFavorites.details = allDetails.first{ it.id == faveId}
            venueFaves.add(venueFavorites)
            venueAndFaves.add(venueAndFavorites)
        }

    }

    override fun removeVenueFavorites(venueFavorites: VenueFavorites) {
        if(venueFaves.contains(venueFavorites)){
            venueFaves.remove(venueFavorites)
            venueAndFaves.remove(venueAndFaves.first{ it.venueFavorites.contains(venueFavorites)})
        }
    }


    override fun getVenueFavorites() : LiveData<List<VenueFavorites>> {
        val liveData = MutableLiveData<List<VenueFavorites>>()
        liveData.value = venueFaves
        return liveData
    }

    override fun getVenueAndFavorites() : LiveData<List<VenueAndFavorites>> {
        val liveData =  MutableLiveData<List<VenueAndFavorites>>()
        liveData.value = venueAndFaves
        return liveData
    }


}